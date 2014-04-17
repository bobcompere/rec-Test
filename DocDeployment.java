/*
 * DocDeployment.java
 *
 * Created on July 8, 2003, 11:42 AM
 */

package com.eisolution.iss.documentation;

import java.util.*;
import java.io.*;
import java.sql.*;
import com.eisolution.iss.fileattachment.*;

import org.apache.log4j.*;


/**
 *
 * @author  Mike Miller
 */
public class DocDeployment {
    
    
    
    static Logger log = Logger.getLogger(DocDeployment.class);
    
    private Connection conn = null;
    private String busProcCd = "";
    private String filePath ="C:\\table-docs\\";
    private HashMap objectTypeCodes = new HashMap();
    private String indexString = "";
    
    /**
     * Creates a new instance of DocDeployment
     * @param pConn Database Connection to use when select documentation data.
     * @param pBusProcCd The business Process Code for the system
     */
    
    
    public  DocDeployment(Connection pConn, String pBusProcCd) {
        
        conn = pConn;
        busProcCd = pBusProcCd;
        init();
    }
    
    /**
     * Sets up the codes lists and some repetitive HTML strings.
     */
    private void init() {
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        
        try {
            sqlQuery =  "SELECT CODE_VALUE, CODE_DESCR "+
            "FROM APPL_CODES COD "+
            "WHERE CODE_GROUP = 'DOCOBJTYPE' "+
            "AND BUS_PROC_CD = ? "+
            "AND CODE_ACTIVE_IND = 'Y' ";
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, busProcCd);
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                String codeValue = rset.getString("code_value");
                String codeDescr = rset.getString("code_descr");
                
                objectTypeCodes.put(codeValue, codeDescr);
            }
        } catch (Exception e) {
            System.out.println("Error populating object type codes:  "+e.getMessage());
        }
        
        createIndexStringHTML();
        
    }
    /**
     *  create the docuemnt set for a specific object type
     *  @param pObjtype - The type of object to generate dccs for.  Valid entries
     *                    should be in the 'DOCOBJTYPE' code group
     */
    public void createDocSetForObjectType(String pObjtype) throws Exception{
        
        PreparedStatement objStmt = null;
        ResultSet objRset = null;
        String objSqlQuery = "";
        
        String htmlString = "";
        
        long dob_id = -1;
        
        //
        // Loop through all of the active documentation objects.  Generate the
        //  custom html page for each object.
        //
        try {
            objSqlQuery = "SELECT DOB_ID "+
            "FROM DOCUMENTATION_OBJECT " +
            "WHERE DOB_TYPE_CD = ? "+
            "AND ACTIVE_IND = 'Y' " +
            "AND bus_proc_cd = ? "+
            "ORDER BY dob_type_cd, dob_name ";
            
            objStmt = conn.prepareStatement(objSqlQuery);
            objStmt.setString(1, pObjtype);
            objStmt.setString(2, busProcCd);
            
            objRset = objStmt.executeQuery();
            
            while (objRset.next()) {
                dob_id = objRset.getLong("dob_id");
                generateObjectHTML(dob_id);
            }
            
        } catch (Exception e) {
            throw new Exception("Error generating object HTML:  "+e.getMessage());
        }
        
        try {
            objRset.close();
            objStmt.clearParameters();
            objStmt.close();
        }catch (Exception e) {
        }
        
    }
    
    /**
     * generate all the html for the documentation set.  Create a table of
     *  contents that will list each object type.  Create a table of contents
     *  for each object type.  Create a page documenting each object.
     */
    public void createCompleteDocSet() throws Exception {
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        
        
        PreparedStatement objStmt = null;
        ResultSet objRset = null;
        String objSqlQuery = "";
        
        String htmlString = "";
        String codeValue = "";
        String codeDescr = "";
        
        long dob_id = -1;
        
        System.out.println("createCompleteDocSet");
        htmlString = "<HTML>";
        htmlString += "<TITLE> Table of Contents </TITLE>";
        htmlString +="\n <LINK Rel='stylesheet' Type='text/css' HREF='style_sheet.css'>";
        htmlString +="</HEAD> \n";
        htmlString += "<BODY> \n";
        
        //
        // Loop through available objects.  Generate the object specific
        //  table of contents, and the master table of contents.
        //
        try {
            
            sqlQuery =  "SELECT CODE_VALUE, CODE_DESCR "+
            "FROM APPL_CODES COD "+
            "WHERE CODE_GROUP = 'DOCOBJTYPE' "+
            "AND BUS_PROC_CD = ? "+
            "AND CODE_ACTIVE_IND = 'Y' ";
            
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, busProcCd);
            
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                codeValue = rset.getString("code_value");
                codeDescr = rset.getString("code_descr");
                
                htmlString += "<br><a href='"+codeValue+".html'>"+codeDescr+"</a> \n";
                
                createTOCForObject(codeValue);
                
            }
        } catch (Exception e) {
            throw new Exception("Error generating object document set:  "+e.getMessage());
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        }catch (Exception e) {
        }
        
        
        try {
            generateHTMLFile(htmlString, "index");
            htmlString= "";
        } catch (Exception e) {
            throw new Exception("Error generating the Master Table of Contents:  "+e.getMessage());
        }
        
        
        //
        // Loop through all of the active documentation objects.  Generate the
        //  custom html page for each object.
        //
        try {
            objSqlQuery = "SELECT DOB_ID "+
            "FROM DOCUMENTATION_OBJECT " +
            "WHERE ACTIVE_IND = 'Y' " +
            "AND bus_proc_cd = ? "+
            "ORDER BY dob_type_cd, dob_name ";
            
            objStmt = conn.prepareStatement(objSqlQuery);
            objStmt.setString(1, busProcCd);
            
            objRset = objStmt.executeQuery();
            
            while (objRset.next()) {
                dob_id = objRset.getLong("dob_id");
                generateObjectHTML(dob_id);
            }
            
        } catch (Exception e) {
            throw new Exception("Error generating object HTML:  "+e.getMessage());
        }
        
        try {
            objRset.close();
            objStmt.clearParameters();
            objStmt.close();
        }catch (Exception e) {
        }
        
        //
        //Export all of the file attachments related to the documentation
        //
        try {
            exportAllFileAttachments();
        }catch (Exception e) {
        }
        
    }
    
    
    /**
     * Generate the table of contents for the specified objtype
     *
     *  @param pObjtype  - the object type for which the table of contents will be
     *                     generated
     */
    public void createTOCForObject(String pObjtype) throws Exception {
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        String sqlQuery = "";
        
        String htmlString = "";
        
        long dobID = -1;
        String dobName = "";
        String dobText = "";
        
        htmlString += "<TITLE> Table of Contents </TITLE>";
        htmlString+="\n <LINK Rel='stylesheet' Type='text/css' HREF='style_sheet.css'>";
        htmlString +="</HEAD> \n";
        htmlString += "<BODY> \n";
        htmlString += indexString;
        htmlString += "<br><center><h1>"+ (String) objectTypeCodes.get(pObjtype)+"</h1></center><br>";
        htmlString += "<CENTER> <TABLE border=1>";
        htmlString += "<TR><TH>Name</TH><TH>Description</TH></TR>";
        
        //
        //  Loop through objects of specifited type
        //  and output the url and description.
        //
        try {
            if (pObjtype.equals("SUBFORM") || pObjtype.equals("STOREDPROC") || pObjtype.equals("EXTERNAL")) {
                sqlQuery = "SELECT dob.dob_id, decode(dob_p.dob_name, null, dob.dob_name, dob_p.dob_name||'.'||dob.dob_name) dob_name, dot.dot_text "+
                "FROM documentation_object dob, documentation_object_text dot, "+
                "     documentation_object dob_p, documentation_object_relation dor_p "+
                "WHERE dob.dob_type_cd = ? "+
                "AND dob.bus_proc_cd = ? "+
                "AND dot.dot2dob(+) = dob.dob_id "+
                "AND dot.dot_section_cd(+) = 'CONDESCR' "+
                "AND   dor_p.dor2dob_uses(+) = dob.dob_id "+
                "AND   dor_p.subobject_ind(+) = 'Y' "+
                "AND   dor_p.dor2dob_used_by = dob_p.dob_id (+)"+
                "AND   dob.active_ind = 'Y' "+
                "ORDER BY dob_p.dob_name, dob_p.dob_seq, dob.dob_name, dob.dob_seq";
            } else {
                sqlQuery = "SELECT dob.dob_id, dob.dob_name, dot.dot_text "+
                "FROM documentation_object dob, documentation_object_text dot "+
                "WHERE dob.dob_type_cd = ? "+
                "AND dob.bus_proc_cd = ? "+
                "AND dot.dot2dob(+) = dob.dob_id "+
                "AND dot.dot_section_cd(+) = 'CONDESCR' "+
                "AND  dob.active_ind = 'Y' "+
                "ORDER BY dob.dob_name ";
            }
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, pObjtype);
            stmt.setString(2, busProcCd);
            
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                dobID = rset.getLong("dob_id");
                
                dobName = rset.getString("dob_name");
                log.debug("TOC:  "+pObjtype+" - "+dobName);
                dobText = rset.getString("dot_text");
                // log.debug(dobName+":  "+dobText);
                if (rset.wasNull()) {
                    dobText = "&nbsp;";
                } else {
                    dobText = parseObjectLink(dobText);
                }
                
                htmlString += "<TR><TD class='name'><a href='"+Long.toString(dobID)+".html'>"+dobName+"</a></TD><TD>"+dobText+"</TD></TR>";
                
            }
        } catch (Exception e) {
            throw new Exception("Error generating table of contents for '"+pObjtype+"':  "+e.getMessage());
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();   
        } catch (Exception e) {
        }
        
        htmlString += "</TABLE></CENTER> "+indexString+" </HTML>";
        generateHTMLFile(htmlString, pObjtype);
    }
    
    
    /**
     * Generate the html page specific to the specifid object.
     * @param pDobID the UID of the object.  The html page that results from
     *   this call will be pDobId.html
     */
    public void generateObjectHTML(long pDobID) throws Exception {
        
        
        String htmlString = "";
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        
        String dobName = "";
        String dobTypeCd = "";
        String dobTypeDescr = "";
        String dobFilename = "";
        
        try {
            sqlQuery = "SELECT decode(dob_p.dob_name, null, dob.dob_name, dob_p.dob_name||'.'||dob.dob_name) dob_name, "+
            "dob.dob_type_cd, cod.code_descr, dob.filename "+
            "FROM documentation_object dob, appl_codes cod,  "+
            "  documentation_object_relation dor, documentation_object dob_p "+
            "WHERE dob.dob_id = ? "+
            "AND   cod.code_group = 'DOCOBJTYPE' "+
            "AND   cod.code_value = dob.dob_type_cd "+
            "AND   cod.bus_proc_cd = dob.bus_proc_cd "+
            "AND   cod.code_active_ind = 'Y' " +
            "AND   dor.dor2dob_uses(+) = dob.dob_id "+
            "AND   dor.subobject_ind(+) = 'Y' "+
            "AND   dor.dor2dob_used_by = dob_p.dob_id(+)";
            
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            rset = stmt.executeQuery();
            
            if (rset.next()) {
                dobName = rset.getString("dob_name");
                dobTypeCd = rset.getString("dob_type_cd");
                dobTypeDescr = rset.getString("code_descr");
                dobFilename = rset.getString("filename");
                if (rset.wasNull()) {
                    dobFilename = "";
                }
            }
            
        } catch (Exception e) {
            throw new Exception("Error getting object details:  "+e.getMessage());
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        try {
            
            log.debug("Generating document for '"+dobName+"' - "+dobTypeCd);
            
            htmlString="<HTML><HEAD><TITLE>"+dobName+" Details </TITLE>";
            htmlString+="\n <LINK Rel='stylesheet' Type='text/css' HREF='style_sheet.css'>";
            htmlString +="</HEAD>\n";
            htmlString+= "<BODY>\n";
            htmlString+= indexString;
            
             
            htmlString+= "<h2><center>"+dobName+"</center></h2>";
            htmlString+= "<br>";
            if (!((dobFilename==null) ||(dobFilename.length() <=0))) {
                htmlString+= "<b>Filename:</b>  "+dobFilename+"<br>";
            }
            
            generateHTMLFile(htmlString, Long.toString(pDobID));
            
            //
            //  GENERATE OBJECT DESCR/SUMMARY
            //
            generateObjTextHTML(pDobID);
            //
            //  GENERATE ERD
            //
            generateObjERD(pDobID);
            //
            //  GENERATE DFD
            //
            generateObjDFD(pDobID);
            
            //
            // misc file attachments
            //
            generateFileAttachmentListHTML(pDobID);
            
            //
            //  LIST COLUMNS OR PARAMS
            //
            if (dobTypeCd.equals("TABLE") || dobTypeCd.equals("VIEW")) {
                generateObjColumnListHTML(pDobID);
            } else if (dobTypeCd.equals("FORM") || dobTypeCd.equals("STOREDPROC") || dobTypeCd.equals("STOREDPACK") || dobTypeCd.equals("REPORT")||dobTypeCd.equals("EXTERNAL")) {
                generateObjParamListHTML(pDobID);
            }
            
            //////////////
            //
            //  Parent object
            //
            /////////////
            
            if (dobTypeCd.equals("SUBFORM")||dobTypeCd.equals("STOREDPROC")||dobTypeCd.equals("EXTERNAL")) {
                generateParentObjectHTML(pDobID);
            }
            //////////////
            //
            // subobjects
            //
            ///////////////
            if (dobTypeCd.equals("STOREDPACK")||dobTypeCd.equals("EXTERNAL")) {
                generateSubObjectHTML(pDobID);
            }
            
            
            //////
            //
            // USES SECTION
            //
            ////
            
            if (!dobTypeCd.equals("TABLE")) {
                
                if (relationExists(pDobID, "USES")) {
                    htmlString = "<H3><CENTER> This "+dobTypeDescr+" uses the following objects.</CENTER></H3>";
                    generateHTMLFile(htmlString, Long.toString(pDobID));
                    
                    //
                    //  LIST TABLES / VIEWS USED
                    //
                    generateUsesHTML(pDobID, "TABLE");
                    generateUsesHTML(pDobID, "VIEW");
                    
                    //
                    //  LIST FORMS USED
                    //
                    generateUsesHTML(pDobID, "FORM");
                    
                    //
                    //  LIST PACKAGES USED
                    //
                    generateUsesHTML(pDobID, "STOREDPACK");
                    generateUsesHTML(pDobID, "STOREDPROC");
                    
                    //
                    // EXTERNAL PROCEDURES
                    //
                    generateUsesHTML(pDobID, "EXTERNAL");
                    
                    //
                    //  LIST REPORTS USED.
                    //
                    generateUsesHTML(pDobID, "REPORT");
                }
            }
            //////
            //
            //  USED BY SECTION
            //
            //////
            if (relationExists(pDobID, "USED_BY")) {
                htmlString = "<H3><CENTER> This "+dobTypeDescr+" is used by the following objects.</CENTER></H3>";
                generateHTMLFile(htmlString, Long.toString(pDobID));
                
                //
                //  LIST SUBSYSTEMS USED BY
                //
                generateUsedByHTML(pDobID,"SUBSYSTEM");
                
                //
                //  LIST FORMS USED BY
                //
                generateUsedByHTML(pDobID,"FORM");
                
                //
                //  LIST PROCEDURES USED BY
                //
                generateUsedByHTML(pDobID,"STOREDPACK");
                generateUsedByHTML(pDobID,"STOREDPROC");
                
                //
                //  LIST VIEWS USED BY
                //
                generateUsedByHTML(pDobID,"VIEW");
                
                //
                // EXTERNAL PROCEDURES
                //
                generateUsedByHTML(pDobID, "EXTERNAL");
                
                //
                // LIST REPORTS USED BY
                //
                generateUsedByHTML(pDobID,"REPORT");
            }
            
            htmlString = indexString;
            // htmlString = "<CENTER><B> [ &nbsp <a href='index.html'>Home</a> &nbsp; | &nbsp <a href='"+dobTypeCd+".html'>"+dobTypeDescr+"</a> &nbsp; ] </B></CENTER>";
            htmlString += "</body> \n </html>";
            
            generateHTMLFile(htmlString, Long.toString(pDobID));
        } catch (Exception e) {
            throw new Exception("Error creating HTML file:  "+e.getMessage());
        }
        
    }
    
    /**
     * create global header/footer links to master object types
     *
     */
    private void createIndexStringHTML() {
        
        int recordCount = 0;
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "SELECT code_value, code_descr "+
        "FROM appl_codes "+
        "WHERE code_group = 'DOCOBJTYPE' "+
        "AND   bus_proc_cd = ? "+
        "AND   code_active_ind = 'Y' ";
        
        try {
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, busProcCd);
            rset = stmt.executeQuery();
            
            indexString = "<BR><CENTER><B><IMG src='logo1.gif' class='logo1'> <IMG src='logo2.gif' class='logo2'> [ &nbsp;";
            
            while (rset.next()) {
                
                
                String code_value = rset.getString("code_value");
                String code_descr = rset.getString("code_descr");
                
                if (recordCount > 0) {
                    indexString += " | &nbsp;";
                }
                
                indexString += " <a href='"+code_value+".html'>"+code_descr+"</a> &nbsp";
                
                recordCount++;
                
            }   
        } catch (Exception e) {
            log.error("Error populating the indexString:  "+e.getMessage());
        }
        
        indexString += "&nbsp;]&nbsp; </B></CENTER><BR>";
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
    }
    
    
    
    /**
     *generateHtmlFile  Writes text to file, creating the file if it doesn't yet exist.
     *
     * @param htmlString  String to write to the file.
     * @param filename     String for the name of the file.
     */
    private void generateHTMLFile(String htmlString, String pFileName) throws Exception {
        
        File f = null;
        FileOutputStream fos = null;
        
        try {
            f = new File(filePath+pFileName+".html");
            fos = new FileOutputStream(f, true);
            fos.write(htmlString.getBytes());
            fos.close();
        }catch (Exception e) {
            throw new Exception("Error writing to the file "+filePath+pFileName+":  "+e.getMessage());
        }
        
    }
    
    /**
     *
     */
    private void generateUsesHTML(long pDobID, String pObjTypeCd) throws Exception {
        
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        
        String objDescr = "";
        
        String htmlString = "";
        
        boolean rowFound = false;
        
        long dobID = -1;
        String dobName = "";
        String dobDescr = "";
        
        
        
        htmlString = "<CENTER>";
        htmlString += "<TABLE border='1' width='95%'>";
        
        
        try {
            objDescr = (String) objectTypeCodes.get(pObjTypeCd);
            
        } catch (Exception e) {
            throw  new Exception("Error generating HTML for '"+pObjTypeCd+"':  "+e.getMessage());
        }
        
        
        htmlString += "<TR><TH COLSPAN='2' class='title'>"+objDescr+"s</TH></TR> \n";
        htmlString +="<TR><TH> Name </TH><TH> Description </TH></TR> \n";
        
        try {
            if (pObjTypeCd.equals("SUBFORM")||pObjTypeCd.equals("STOREDPROC")||pObjTypeCd.equals("EXTERNAL"))  {
                //Generate list to include parent item (package.function or form.subform)
                sqlQuery = "SELECT dob.dob_id, decode(dob_p.dob_name, null, dob.dob_name, dob_p.dob_name||'.'||dob.dob_name) dob_name, "+
                "       dor.dor_descr, dot.dot_text "+
                "FROM documentation_object_relation dor, "+
                "     documentation_object dob, "+
                "     documentation_object_text dot, "+
                "     documentation_object_relation dor_p, "+
                "     documentation_object dob_p "+
                "WHERE dor.dor2dob_used_by = ? "+
                "AND   dor.dor2dob_uses = dob.dob_id "+
                "AND   dor.subobject_ind  = 'N' "+
                "AND   dob.dob_type_cd =? "+
                "AND   dot.dot2dob(+) = dob.dob_id "+
                "AND   dot.dot_section_cd(+) = 'CONDESCR' "+
                "AND   dor.active_ind = 'Y' "+
                "AND   dob.active_ind = 'Y' "+
                "AND   dor_p.dor2dob_uses(+) = dob.dob_id "+
                "AND   dor_p.subobject_ind(+) = 'Y' "+
                "AND   dor_p.dor2dob_used_by = dob_p.dob_id(+)"+
                "AND   dob.dob_id <> ? ";
            } else {
                
                sqlQuery = "SELECT dob.dob_id, dob.dob_name, "+
                "       dor.dor_descr, dot.dot_text "+
                "FROM documentation_object_relation dor, "+
                "     documentation_object dob, "+
                "     documentation_object_text dot "+
                "WHERE dor.dor2dob_used_by = ? "+
                "AND   dor.dor2dob_uses = dob.dob_id "+
                "AND   dor.subobject_ind = 'N' "+
                "AND   dob.dob_type_cd = ? "+
                "AND   dot.dot2dob(+) = dob.dob_id "+
                "AND   dot.dot_section_cd(+) = 'CONDESCR' "+
                "AND   dor.active_ind = 'Y' "+
                "AND   dob.active_ind = 'Y' "+
                "AND   dob.dob_id <> ? ";
            }
            
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            stmt.setString(2, pObjTypeCd);
            stmt.setLong(3, pDobID);
            
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                rowFound = true;
                
                dobID = rset.getLong("dob_id");
                dobName = rset.getString("dob_name");
                dobDescr = rset.getString("dor_descr");
                if (rset.wasNull()) {
                    dobDescr = rset.getString("dot_text");
                    if (rset.wasNull()) {
                        dobDescr = "&nbsp;";
                    } else {
                        dobDescr = parseObjectLink(dobDescr);
                    }
                }
                
                htmlString +="<TR><TD class='name'><a href='"+Long.toString(dobID)+".html'>"+dobName+"</a></td><td>"+dobDescr+"</td></tr> \n";
                
            }
            
        } catch (Exception e) {
            throw new Exception("Error generating uses summary for '"+objDescr+"':  "+e.getMessage());
            
        }
        
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        htmlString += "</TABLE></CENTER><BR>";
        
        if (rowFound) {
            generateHTMLFile(htmlString, Long.toString(pDobID));
        }
        
    }
    
    /**
     *  List the objects of specified type that use this object.
     */
    private void generateUsedByHTML(long pDobID, String pObjTypeCd)  throws Exception{
        
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        
        String objDescr = "";
        
        String htmlString = "";
        
        boolean rowFound = false;
        
        long dobID = -1;
        String dobName = "";
        String dobDescr = "";
        
        
        
        htmlString = "<CENTER>";
        htmlString += "<TABLE border='1' width='95%'>";
        
        
        try {
            objDescr = (String) objectTypeCodes.get(pObjTypeCd);
            
        } catch (Exception e) {
            throw  new Exception("Error generating HTML for '"+pObjTypeCd+"':  "+e.getMessage());
        }
        
        
        htmlString += "<TR><TH COLSPAN='2' class='title'>"+objDescr+"s</TH></TR> \n";
        htmlString +="<TR><TH> Name </TH><TH> Description </TH></TR> \n";
        
        try {
            if (pObjTypeCd.equals("SUBFORM")||pObjTypeCd.equals("STOREDPROC")||pObjTypeCd.equals("EXTERNAL"))  {
                //Generate list to include parent item (package.function or form.subform)
                sqlQuery = "SELECT dob.dob_id, decode(dob_p.dob_name, null, dob.dob_name, dob_p.dob_name||'.'||dob.dob_name) dob_name, "+
                "       dor.dor_descr, dot.dot_text "+
                "FROM documentation_object_relation dor, "+
                "     documentation_object dob, "+
                "     documentation_object_text dot, "+
                "     documentation_object_relation dor_p, "+
                "     documentation_object dob_p "+
                "WHERE dor.dor2dob_uses = ? "+
                "AND   dor.dor2dob_used_by = dob.dob_id "+
                "AND   dor.subobject_ind = 'N' "+
                "AND   dob.dob_type_cd =? "+
                "AND   dot.dot2dob(+) = dob.dob_id "+
                "AND   dot.dot_section_cd(+) = 'CONDESCR' "+
                "AND   dor.active_ind = 'Y' "+
                "AND   dob.active_ind = 'Y' "+
                "AND   dor_p.dor2dob_uses(+) = dob.dob_id "+
                "AND   dor_p.subobject_ind(+) = 'Y' "+
                "AND   dor_p.dor2dob_used_by = dob_p.dob_id (+) "+
                "AND   dob.dob_id <> ? ";
            } else {
                sqlQuery = "SELECT dob.dob_id, dob.dob_name, "+
                "       dor.dor_descr, dot.dot_text "+
                "FROM documentation_object_relation dor, "+
                "     documentation_object dob, "+
                "     documentation_object_text dot "+
                "WHERE dor.dor2dob_uses = ? "+
                "AND   dor.dor2dob_used_by = dob.dob_id "+
                "AND   dor.subobject_ind = 'N' "+
                "AND   dob.dob_type_cd =? "+
                "AND   dot.dot2dob(+) = dob.dob_id "+
                "AND   dot.dot_section_cd(+) = 'CONDESCR' "+
                "AND   dor.active_ind = 'Y' "+
                "AND   dob.active_ind = 'Y' "+
                "AND   dob.dob_id <> ? ";
            }
            
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            stmt.setString(2, pObjTypeCd);
            stmt.setLong(3, pDobID);
            
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                rowFound = true;
                
                dobID = rset.getLong("dob_id");
                dobName = rset.getString("dob_name");
                dobDescr = rset.getString("dor_descr");
                if (rset.wasNull()) {
                    dobDescr = rset.getString("dot_text");
                    if (rset.wasNull()) {
                        dobDescr = "&nbsp;";
                    } else {
                        dobDescr = parseObjectLink(dobDescr);
                    }
                }
                
                htmlString +="<TR><TD class='name'><a href='"+Long.toString(dobID)+".html'>"+dobName+"</a></td><td>"+dobDescr+"</td></tr> \n";
                
            }
            
        } catch (Exception e) {
            throw new Exception("Error generating used by summary for '"+objDescr+"':  "+e.getMessage());
        }
        
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        htmlString += "</TABLE></CENTER><BR>";
        
        if (rowFound) {
            generateHTMLFile(htmlString, Long.toString(pDobID));
        }
        
    }
    
    
    
    /**
     *  List the sub objects for this object.  For stored packages, list the
     *  functions for
     */
    private void generateSubObjectHTML(long pDobID)  throws Exception{
        
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        
        String objDescr = "";
        
        String htmlString = "";
        
        boolean rowFound = false;
        
        long dobID = -1;
        String dobName = "";
        String dobDescr = "";
        
        
        
        htmlString = "<CENTER> \n";
        htmlString += "<TABLE border=1>";
        
        
        
        htmlString += "<TR><TH COLSPAN='2' class='title'>Subcomponents</TH></TR> \n";
        htmlString +="<TR><TH> Name </TH><TH> Description </TH></TR> \n";
        
        try {
            sqlQuery = "SELECT dob.dob_id, dob.dob_name, "+
            "       dor.dor_descr, dot.dot_text "+
            "FROM documentation_object_relation dor, "+
            "     documentation_object dob, "+
            "     documentation_object_text dot "+
            "WHERE dor.dor2dob_used_by = ? "+
            "AND   dor.dor2dob_uses = dob.dob_id "+
            "AND   dot.dot2dob(+) = dob.dob_id "+
            "AND   dot.dot_section_cd(+) = 'CONDESCR' "+
            "AND   dor.subobject_ind = 'Y' "+
            "AND   dor.active_ind = 'Y' "+
            "AND   dob.active_ind = 'Y' ";
            
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                rowFound = true;
                
                dobID = rset.getLong("dob_id");
                dobName = rset.getString("dob_name");
                dobDescr = rset.getString("dor_descr");
                if (rset.wasNull()) {
                    dobDescr = rset.getString("dot_text");
                    if (rset.wasNull()) {
                        dobDescr = "&nbsp;";
                    } else {
                        dobDescr = parseObjectLink(dobDescr);
                    }
                }
                
                htmlString +="<TR><TD class='name'><a href='"+Long.toString(dobID)+".html'>"+dobName+"</a></td><td>"+dobDescr+"</td></tr> \n";
                
            }
            
        } catch (Exception e) {
            throw new Exception("Error generating sub-object summary:  "+e.getMessage());
        }
        
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        htmlString += "</TABLE></CENTER>\n";
        
        if (rowFound) {
            generateHTMLFile(htmlString, Long.toString(pDobID));
        }
        
    }
    
    
    /**
     *  List the parent object.
     */
    private void generateParentObjectHTML(long pDobID)  throws Exception{
        
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        
        String objDescr = "";
        
        String htmlString = "";
        
        boolean rowFound = false;
        
        long dobID = -1;
        String dobName = "";
        String dobDescr = "";
        
        
        htmlString = "<CENTER>\n<BR>";
        htmlString += "<TABLE border=1>";
        
        
        
        htmlString += "<TR><TH COLSPAN='2' class='title'>Parent Object</TH></TR> \n";
        htmlString +="<TR><TH> Name </TH><TH> Description </TH></TR> \n";
        
        try {
            sqlQuery = "SELECT dob.dob_id, dob.dob_name, "+
            "       dor.dor_descr, dot.dot_text "+
            "FROM documentation_object_relation dor, "+
            "     documentation_object dob, "+
            "     documentation_object_text dot "+
            "WHERE dor.dor2dob_used_by = dob.dob_id "+
            "AND   dor.dor2dob_uses = ? "+
            "AND   dot.dot2dob(+) = dob.dob_id "+
            "AND   dot.dot_section_cd(+) = 'CONDESCR' "+
            "AND   dor.subobject_ind = 'Y' "+
            "AND   dor.active_ind = 'Y' "+
            "AND   dob.active_ind = 'Y' ";
            
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                rowFound = true;
                
                dobID = rset.getLong("dob_id");
                dobName = rset.getString("dob_name");
                dobDescr = rset.getString("dor_descr");
                if (rset.wasNull()) {
                    dobDescr = rset.getString("dot_text");
                    if (rset.wasNull()) {
                        dobDescr = "&nbsp;";
                    } else {
                        dobDescr = parseObjectLink(dobDescr);
                    }
                }
                
                htmlString +="<TR><TD class='name'><a href='"+Long.toString(dobID)+".html'>"+dobName+"</a></td><td>"+dobDescr+"</td></tr> \n";
                
            }
            
        } catch (Exception e) {
            throw new Exception("Error generating parent description. for '"+objDescr+"':  "+e.getMessage());
        }
        
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        htmlString += "</TABLE></CENTER>\n";
        
        if (rowFound) {
            generateHTMLFile(htmlString, Long.toString(pDobID));
        }
        
    }
    
    
    
    /**
     * List the columns, for the specified object
     */
    private void  generateObjColumnListHTML(long pDobID) throws Exception {
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        boolean rowFound = false;
        
        String htmlString = "";
        
        String docName = "";
        String docType = "";
        String docSize = "";
        String docDefault = "";
        String docNullInd = "";
        String docPKInd = "";
        String docFKInd = "";
        String docFKTable = "";
        String docFKColumn = "";
        String docDescr = "";
        long docFKID = -1;
        
        
        htmlString = "<CENTER>\n<TABLE border='1'> \n";
        htmlString += "<TR><TH COLSPAN='9' class='title'>Column Listing</TH></TR> \n";
        htmlString += "<TR><TH>Name</TH>";
        htmlString += "<TH>Type</TH>";
        htmlString += "<TH>Size</TH>";
        htmlString += "<TH>Default</TH>";
        htmlString += "<TH>Null</TH>";
        htmlString += "<TH>PK</TH>";
        htmlString += "<TH>FK</TH>";
        htmlString += "<TH>FK Reference</TH>";
        htmlString += "<TH>Description</TH></TR>";
        
        
        
        try {
            sqlQuery = "SELECT doc.doc_name, doc.doc_type_cd, doc.doc_size, "+
            "       doc.doc_nullable_ind, doc.doc_default, "+
            "       doc.doc_pk_ind, doc.doc_fk_ind, doc.doc_descr, "+
            "       doc.doc_seq, doc.doc2dob_fk, doc.doc2doc_fk, "+
            "       dob_fk.dob_name doc_fk_table, doc_fk.doc_name doc_fk_column " +
            "FROM DOCUMENTATION_OBJECT_COLUMN doc, "+
            " DOCUMENTATION_OBJECT_COLUMN doc_fk,  "+
            " DOCUMENTATION_OBJECT dob_fk "+
            "WHERE doc.doc2dob = ? "+
            "AND doc.active_ind = 'Y' "+
            "AND doc.doc2dob_fk = dob_fk.dob_id(+) "+
            "AND doc.doc2doc_fk = doc_fk.doc_id(+) "+
            "ORDER BY doc.doc_seq, doc.doc_name ";
            
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                
                rowFound = true;
                
                docName = rset.getString("doc_name");
                docType = rset.getString("doc_type_cd");
                docSize = rset.getString("doc_size");
                if (rset.wasNull()) {
                    docSize = "&nbsp";
                }
                docNullInd = rset.getString("doc_nullable_ind");
                docDefault = rset.getString("doc_default");
                if (rset.wasNull()) {
                    docDefault = "&nbsp;";
                }
                docPKInd = rset.getString("doc_pk_ind");
                docFKInd = rset.getString("doc_fk_ind");
                if (docFKInd.equals("Y")) {
                    docFKTable = rset.getString("doc_fk_table");
                    docFKColumn = rset.getString("doc_fk_column");
                    docFKID = rset.getLong("doc2dob_fk");
                } else {
                    docFKTable = "";
                    docFKColumn = "";
                }
                docDescr = rset.getString("doc_descr");
                if (rset.wasNull()) {
                    docDescr = "&nbsp;";
                } else {
                    docDescr = parseObjectLink(docDescr);
                }
                
                htmlString += "<TR><TD class='name'>"+docName+"</TD>";
                htmlString += "<TD>"+docType+"</TD>";
                htmlString += "<TD>"+docSize+"</TD>";
                htmlString += "<TD>"+docDefault+"</TD>";
                htmlString += "<TD>"+docNullInd+"</TD>";
                if (docPKInd.equals("Y")) {
                    htmlString += "<TD align=center valign=center>X</TD>";
                } else  {
                    htmlString += "<TD> &nbsp </TD>";
                }
                
                
                if (docFKInd.equals("Y") && (docFKTable!=null)) {
                    htmlString += "<TD align=center valign=center>X</TD><TD><FONT SIZE='-1'><a href='"+Long.toString(docFKID)+".html'>"+docFKTable+"."+docFKColumn+"</a></FONT></TD>";
                } else  {
                    htmlString += "<TD> &nbsp </TD><TD> &nbsp; </TD>";
                }
                htmlString += "<TD>"+docDescr+"</TD>";
                
            }
            
            htmlString += "</TABLE></CENTER>";
        } catch (Exception e) {
            throw new Exception("Error generating columns:  "+e.getMessage());
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        if (rowFound) {
            generateHTMLFile(htmlString, Long.toString(pDobID));
        }
        
    }
    
    
    /**
     *  Generate a description of the parameters for the subsystem
     *
     *
     */
    private void generateObjParamListHTML(long pDobID) throws Exception {
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        boolean rowFound = false;
        
        String htmlString = "";
        
        String dopName = "";
        String dopIn = "";
        String dopOut = "";
        String dopReturn = "";
        String dopType = "";
        String dopDefault = "";
        String dopDescr = "";
        String dopSeq = "";
        
        
        htmlString = "<CENTER><TABLE border=1> \n";
        htmlString += "<TR><TH COLSPAN='7' class='title'>Parameters</TH></TR> \n";
        htmlString += "<TR><TH>Name</TH>";
        htmlString += "<TH>In</TH>";
        htmlString += "<TH>Out</TH>";
        htmlString += "<TH>Return</TH>";
        htmlString += "<TH>Type</TH>";
        htmlString += "<TH>Default</TH>";
        htmlString += "<TH>Description</TH></TR> \n";
        
        try {
            
            sqlQuery = "SELECT dop.dop_id, dop.dop_name, dop.dop_in_ind, "+
            "       dop.dop_out_ind, dop.dop_return_ind, dop.dop_default, "+
            "       dop.dop_descr, dop.dop_type_cd, dop.dop_seq "+
            "FROM documentation_object_param dop "+
            "WHERE dop.dop2dob = ? "+
            "AND dop.active_ind = 'Y' "+
            "ORDER BY dop.dop_return_ind,  dop.dop_seq ";
            
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                
                rowFound=true;
                
                dopName = rset.getString("dop_name");
                if (rset.wasNull()) {
                    dopName = "&nbsp;";
                }
                dopIn = rset.getString("dop_in_ind");
                dopOut = rset.getString("dop_out_ind");
                dopReturn = rset.getString("dop_return_ind");
                dopType = rset.getString("dop_type_cd");
                if (dopReturn.equals("Y")){
                    dopName = "Return";
                }
                dopDefault = rset.getString("dop_default");
                if (rset.wasNull()) {
                    dopDefault = "&nbsp;";
                }
                dopDescr = rset.getString("dop_descr");
                if (rset.wasNull()) {
                    dopDescr = "&nbsp;";
                } else {
                    dopDescr = parseObjectLink(dopDescr);
                }
                htmlString += "<TR><TD class='name'>"+dopName+"</TD>";
                htmlString += "<TD>"+dopIn+"</TD>";
                htmlString += "<TD>"+dopOut+"</TD>";
                htmlString += "<TD>"+dopReturn+"</TD>";
                htmlString += "<TD>"+dopType+"</TD>";
                htmlString += "<TD>"+dopDefault+"</TD>";
                htmlString += "<TD>"+dopDescr+"</TD></TR> \n";
                
            }
            htmlString += "</TABLE></CENTER>";
        } catch (Exception e) {
            throw new Exception("Error generating parameters:  "+e.getMessage());
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        if (rowFound) {
            generateHTMLFile(htmlString, Long.toString(pDobID));
        }
        
    }
    
    /**
     * create a link to the object ERDs
     *
     */
    private void generateObjERD(long pDobID) throws Exception {
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        boolean rowFound = false;
        
        String htmlString = "";
        
        String dorDescr = "";
        String erdID = "";
        String attID = "";
        String filetype = "";
        String dobName = "";
        
        try {
            rowFound = true;
            
            sqlQuery = "SELECT dor.dor_descr, dob.dob_id, att.att_id, att.filetype, dob.dob_name "+
            "FROM documentation_object_relation dor, "+
            "     documentation_object dob, "+
            "     file_attachment att "+
            "WHERE dor.dor2dob_used_by = ? "+
            "AND   dor.dor2dob_uses = dob.dob_id "+
            "AND   att.objtype = 'DOB' "+
            "AND   att.obj_id = dob.dob_id "+
            "AND   dob.dob_type_cd = 'ERD' "+
            "AND   dob.active_ind = 'Y' "+
            "AND   dor.active_ind = 'Y' " +
            "AND   att.filetype not in ('HTM', 'HTML') "+
            "ORDER BY dor.dor_seq ";;
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            
            rset = stmt.executeQuery();
            while (rset.next()) {
                dorDescr = rset.getString("dor_descr");
                if (rset.wasNull()) {
                    dorDescr = "&nbsp;";
                }
                erdID = rset.getString("dob_id");
                attID = rset.getString("att_id");
                filetype = rset.getString("filetype"   );
                dobName = rset.getString("dob_name");
                
                htmlString +="<BR><CENTER><a href='"+erdID+".html'><IMG border='0' src='"+attID+"."+filetype.toLowerCase()+"'></a><br>"+dobName+": "+dorDescr+"</CENTER><br>";
                
            }
            
            
            
        } catch (Exception e) {
            throw new Exception("Error linking to ERD attachment:  "+e.getMessage());
            
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        if (rowFound) {
            generateHTMLFile(htmlString, Long.toString(pDobID));
        }
        
        
    }
    
    /**
     * generateObjDFD - create the DFDs for the specified object.
     *
     */
    private void generateObjDFD(long pDobID) throws Exception {
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        boolean rowFound = false;
        
        String htmlString = "";
        
        String dorDescr = "";
        String dfdID = "";
        String attID = "";
        String filetype = "";
        String dobName = "";
        
        try {
            
            sqlQuery = "SELECT dor.dor_descr, dob.dob_id, att.att_id, att.filetype, dob.dob_name "+
            "FROM documentation_object_relation dor, "+
            "     documentation_object dob, "+
            "     file_attachment att "+
            "WHERE dor.dor2dob_used_by = ? "+
            "AND   dor.dor2dob_uses = dob.dob_id "+
            "AND   att.objtype = 'DOB' "+
            "AND   att.obj_id = dob.dob_id "+
            "AND   dob.dob_type_cd = 'DFD' "+
            "AND   dob.active_ind = 'Y' "+
            "AND   dor.active_ind = 'Y' " +
            "AND   att.filetype not in ('HTM', 'HTML') "+
            "ORDER BY dor.dor_seq ";
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            
            rset = stmt.executeQuery();
            while (rset.next()) {
                rowFound = true;
                
                dorDescr = rset.getString("dor_descr");
                if (rset.wasNull()) {
                    dorDescr = "";
                };
                dfdID = rset.getString("dob_id");
                attID = rset.getString("att_id");
                filetype = rset.getString("filetype");
                dobName = rset.getString("dob_name");
                
                htmlString +="<CENTER><a href='"+dfdID+".html'><IMG border='0' src='"+attID+"."+filetype.toLowerCase()+"'></a><br>"+dobName+": "+dorDescr+"</CENTER><br>";
                
            }
            
            
            
        } catch (Exception e) {
            throw new Exception("Error linking to DFD attachment:  "+e.getMessage());
            
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        if (rowFound) {
            generateHTMLFile(htmlString, Long.toString(pDobID));
        }
        
        
    }
    
    
    /**
     * Create the file attahcment list for the specified object.
     *
     */
    private void generateFileAttachmentListHTML(long pDobID) throws Exception {
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        boolean rowFound = false;
        
        String htmlString = "";
        
        String attachName = "";
        String attID = "";
        String filetype = "";
        
        try {
            
            sqlQuery = "SELECT att.att_id, att.filetype, att.attach_name "+
            "FROM file_attachment att "+
            "WHERE att.objtype = 'DOB' "+
            "AND   att.obj_id = ? "+
            "AND   att.filetype not in ('HTM', 'HTML')";
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            
            rset = stmt.executeQuery();
            while (rset.next()) {
                rowFound = true;
                
                attID = rset.getString("att_id");
                filetype = rset.getString("filetype");
                attachName = rset.getString("attach_name");
                
                htmlString +="<CENTER><A href='"+attID+"."+filetype.toLowerCase()+"'><IMG border='0' src='"+attID+"."+filetype.toLowerCase()+"'></a><br>"+attachName+"</CENTER><br>";
                
            }
            
            
            
        } catch (Exception e) {
            throw new Exception("Error linking to DFD attachment:  "+e.getMessage());
            
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        if (rowFound) {
            generateHTMLFile(htmlString, Long.toString(pDobID));
        }
        
        
    }
    
    
    /**
     *  Export all of the non html File Attachments
     *
     */
    private void exportAllFileAttachments() throws Exception {
        
        FileAttLOB fal = new FileAttLOB(conn);
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        
        String filetype = "";
        long att_id = -1;
        long file_lob_id = -1;
        
        try {
            sqlQuery = "SELECT att_id, filetype, fal.att_lob_id  "+
            "FROM file_att_lob fal, file_attachment att "+
            "WHERE fal.fal2att = att.att_id "+
            "AND   att.objtype = 'DOB' "+
            "AND   att.filetype not in ('HTML', 'HTM') ";
            
            
            stmt = conn.prepareStatement(sqlQuery);
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                att_id = rset.getLong("att_id");
                filetype = rset.getString("filetype");
                file_lob_id = rset.getLong("att_lob_id");
                
                fal.exportLOB(file_lob_id, filePath+Long.toString(att_id)+"."+filetype.toLowerCase());
                log.debug("filename = "+filePath+Long.toString(att_id)+"."+filetype.toLowerCase());
            }
        } catch (Exception e) {
            throw new Exception("Error exporting file attachments:  "+e.getMessage());
        }
        
        try {
            rset.close();
            stmt.close();
        } catch (Exception e) {
        }
        
    }
    
    
    /**
     * Generate text realating to this object.
     *
     */
    private void generateObjTextHTML(long pDobID) throws Exception {
        
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlQuery = "";
        boolean rowFound = false;
        
        String htmlString = "";
        
        String dotText = "";
        String dotTitle = "";
        
        try {
            sqlQuery = "SELECT dot.dot_text, dot.dot_title "+
            "FROM documentation_object_text dot "+
            "WHERE dot.dot2dob = ? "+
            "AND   dot.active_ind = 'Y'"+
            "ORDER BY dot_section_cd, dot_seq ";
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                rowFound=true;
                
                dotText = rset.getString("dot_text");
                if (rset.wasNull()) {
                    dotText = "&nbsp;";
                } else {
                    dotText = parseObjectLink(dotText);
                }
                dotTitle = rset.getString("dot_title");
                if (rset.wasNull()) {
                    dotTitle="";
                } else {
                    htmlString += "<p class='dot_title'> <b> "+dotTitle+"</b></p>";
                }
                
                htmlString += "<p class='dot_text'>"+dotText+"</p><br>\n";
                
            }
        } catch (Exception e) {
            throw new Exception("Error generating object text:  "+e.getMessage());
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e) {
        }
        
        if (rowFound) {
            generateHTMLFile(htmlString, Long.toString(pDobID));
        }
    }
    
    
    /**
     * Parse the given string replacing references to other documentation_objects
     *  with links to those objects.  For example <TABLE>FILE_ATTACHMENT</TABLE>
     *  will be replaced with <a href="url_to_file_attachment_table">FILE_ATTACHMENT</a>
     * 
     * @param pStringToParse
     * @return
     * @throws Exception
     */
    private String parseObjectLink(String pStringToParse) throws Exception {
        String parsedString = pStringToParse;
        int token1Loc = 0;
        int token2Loc = 0;
        int token3Loc = 0;
        int token4Loc = 0;
        int bufferLength = 0;
        
        String dobType = "";
        String dobName = "";
        String dobId = "0";
        boolean bCloseTagFound = false;
        
        //get start of tag.
        token1Loc = parsedString.indexOf("<");
        while (token1Loc > 0) {
            
            //Get end of tag.
            token2Loc = parsedString.indexOf(">", token1Loc);
            
            if (token2Loc > token1Loc) {
                //Get tag value
                dobType = parsedString.substring(token1Loc+1, token2Loc);
                
                
                if ( objectTypeCodes.containsKey(dobType)) {
                    
                    
                    //Get closing tag.
                    token3Loc=parsedString.indexOf("</", token2Loc);
                    
                    //If there is no closing token, then set it to the last character of the string.
                    if (token3Loc < 0) {
                        token3Loc = parsedString.length() - 1;
                        token4Loc = parsedString.length() - 1;
                    } else {
                      token4Loc=parsedString.indexOf(">", token3Loc);
                    }
                    if (token4Loc < 0) {
                        token4Loc = parsedString.length() - 1;
                    }
                    
                //   log.debug("\n1: "+token1Loc+"\n2: "+token2Loc+"\n3: "+token3Loc+"\n4: "+token4Loc);
                    
                    //Make sure we are closing the correct item.
                    while (!bCloseTagFound && token3Loc > 0 && token4Loc > 0 && token3Loc < parsedString.length()-3) {
                        if ((parsedString.substring(token3Loc+2, token4Loc)).equals(dobType)) {
                            bCloseTagFound = true;
                        } else {
                            token3Loc=parsedString.indexOf("</", token4Loc);
                            token4Loc=parsedString.indexOf(">", token3Loc);
                        }
                    }
                    if (bCloseTagFound) {
                        dobName = parsedString.substring(token2Loc+1, token3Loc);
                        dobId = getDobId(dobName, dobType);
                        
                    } else {
                        dobId = "";
                    }
                    
                    if (dobId != null && dobId.length() > 0) {
                        parsedString = parsedString.substring(0, token1Loc) + "<a href='"+getDobId(dobName, dobType)+".html'>"+parsedString.substring(token2Loc+1, token3Loc)+"</a>"+parsedString.substring(token4Loc+1);
                        
                    }
                } else {
                    token4Loc = token2Loc+1;
                }
                
            } else {
                token4Loc = token2Loc;
            }
            
            token1Loc = parsedString.indexOf("<", token4Loc);
            
        }
        
        return parsedString;
    }
    
    
    /**
     *   Look up the documenation_object uid for the given object name/type
     * 
     * @param dobName
     * @param dobType
     * @return
     * @throws Exception
     */
    private String getDobId(String dobName, String dobType) throws Exception{
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sqlStatement = "";
        String dob_id = "";
        
        try {
            sqlStatement = "SELECT TO_CHAR(dob_id) dob_id "+
            "FROM  documentation_object " +
            "WHERE dob_type_cd = ? "+
            "AND dob_name = ? ";
            
            stmt = conn.prepareStatement(sqlStatement);
            stmt.setString(1, dobType);
            stmt.setString(2, dobName);
            
            rset = stmt.executeQuery();
            if (rset.next()) {
                dob_id = rset.getString("dob_id");
            }
            
        } catch (Exception e) {
            log.error("Error selecting dob_id for "+dobType+":"+dobName+":  "+e.getMessage());
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e){
        }
        
        return dob_id;
        
        
        
    }

        private boolean relationExists(long pDobID, String pRelationType) {
        
        String sqlQuery = "";
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        boolean bRelExists = false;
        
        try {
            
            if (pRelationType.equals("USES")) {
                sqlQuery = "SELECT dor_id " +
                "FROM documentation_object_relation dor, documentation_object dob " +
                "WHERE dor.dor2dob_used_by = ? "+
                "AND dor.dor2dob_uses = dob.dob_id "+
                "AND dob.active_ind = 'Y' "+
                "AND dor.subobject_ind <> 'Y' ";
            } else {
                sqlQuery = "SELECT dor_id " +
                "FROM documentation_object_relation dor, documentation_object dob " +
                "WHERE dor.dor2dob_uses = ? "+
                "AND dor.dor2dob_used_by = dob.dob_id "+
                "AND dob.active_ind = 'Y' "+
                "AND dor.subobject_ind <> 'Y' ";
            }
            
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setLong(1, pDobID);
            rset = stmt.executeQuery();
            
            if (rset.next()){
                bRelExists = true;
            } else {
                bRelExists = false;
            }
            
        }catch (Exception E){
        }
        
        try {
            rset.close();
            stmt.clearParameters();
            stmt.close();
        } catch (Exception e){
        }
        
        return bRelExists;
        
    }
    
    
    /**
     * Generate the documenation.
     *
     */
    public static void main(String argv[]) {
        
        Connection c = null;
        
        String mBusProcCd = "XXXX";
        
        String dbDriver = "oracle.jdbc.driver.OracleDriver";
        String dbUrl = "jdbc:oracle:thin:@192.168.100.10:1521:XXXX";
        String dbUser = "XXXX";
        String dbPasswd ="XXXX";
        
        String errMsg = "";
        System.out.println(argv.toString());
        if (argv.length < 1) {
            System.err.println("Usage DocDeployment [FULL|TOC|DOCSET|OBJECT] <type>");
            return;
        }
        try {
            Class.forName(dbDriver).newInstance();
        } catch (Exception e) {
            errMsg = "Error loading driver:"+e.getMessage();
        }
        try {
         
            c = DriverManager.getConnection(dbUrl,dbUser,dbPasswd);
            
            if (c.isClosed()  ) {
                throw new Exception("Connection not open");
            }
            
        } catch (Exception E) {
            System.out.println("Login refused "+E.getMessage());
            System.exit(1);
        }
        
        try {
            
            DocDeployment dl = new DocDeployment(c, mBusProcCd);
           
            if (argv[0].equals( "FULL")) {
                
                dl.createCompleteDocSet();
            } else if (argv[0].equals("TOC")) {
                if (argv.length < 2) {
                    System.err.println("Usage DocDeployment TOC <type>");
                } else { 
                    dl.createTOCForObject(argv[1]);
                }
            } else if (argv[0].equals("DOCSET")) {
                if (argv.length < 2) {
                    System.err.println("Usage DocDeployment DOCSET <type>");
                } else { 
                    dl.createDocSetForObjectType(argv[1]);
                }
            } else if (argv[0].equals("OBJECT")) {
                if (argv.length < 2) {
                    System.err.println("Usage DocDeployment OBJECT <type>");
                } else { 
                    dl.generateObjectHTML(Long.parseLong(argv[1]));
                }
            } else {
                throw new Exception ("Unsupported action:  "+ argv[1]);
            }
            
            
            //dl.createCompleteDocSet();
            //dl.createTOCForObject("STOREDPROC");
            //dl.createDocSetForObjectType("SUBSYSTEM");
            //dl.createTOCForObject("EXTERNAL");
            //dl.createDocSetForObjectType("REPORT");
            //dl.exportAllFileAttachments();
            //dl.createTOCForObject("TABLE");
            //dl.createTOCForObject("VIEW");
            //dl.generateObjectHTML(1610618379);
        }  catch (Exception e) {
            System.out.println("Error:  "+e.getMessage());
        }
        try {
            c.close();
        } catch (Exception e) {
        }
        
        System.out.println("DONE");
        
    }
    
    
        
}



