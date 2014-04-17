<!--

//
// The writeDate function endeavors to work with as many interpretations
// of the JavaScript/ECMAScript standard as possible. So there.
//
// Within <HEAD> tag, place the following:
//
// <script language="JavaScript" type="text/javascript"
//    src="showdate.js"></script>
//
// Within <BODY> tag, place the following, making sure to
// place comment tags around the actual script:
//
// <script language="JavaScript" type="text/javascript">
// if (writeDate) writeDate();
// </script>
//

// Get around possible lack of Array() operator
function makeMonthArray() {
    this.length=12;
    this[1]  = "Jan.";  this[2]  = "Feb."; this[3]  = "March";
    this[4]  = "April"; this[5]  = "May";  this[6]  = "June";
    this[7]  = "July";  this[8]  = "Aug."; this[9]  = "Sept.";
    this[10] = "Oct.";  this[11] = "Nov."; this[12] = "Dec.";
    return this;
}

// Get around possible lack of Array() operator
function makeDayArray() {
    this.length=7;
    this[1] = "Sun."; this[2] = "Mon.";   this[3] = "Tues.";
    this[4] = "Wed."; this[5] = "Thurs."; this[6] = "Fri.";
    this[7] = "Sat.";
    return this;
}

// Function to be used in case getFullYear is not available
// Thanks to David Flanagan ("JavaScript, The Definitive Guide")
// Works with dates after the year 1000 and in various forms
//  (e.g., 99 for 1999, 100 for 2000, 2010, etc.).
function _getFullYear() {
    var y = this.getYear();
    if (y < 1000) y += 1900;
    return y;
}

// Write a date of the form "Day., Month DD, YYYY"
function writeDate() {
    now = new Date();
    if (!now.getFullYear) now.getFullYear = _getFullYear;
    monthName = new makeMonthArray(); dayName = new makeDayArray();

    document.write (dayName[now.getDay() + 1] + ", " + monthName[now.getMonth() + 1] + " " + now.getDate() + ", " + now.getFullYear());
}

//->
