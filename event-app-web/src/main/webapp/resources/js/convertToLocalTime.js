//function for converting UTC datetime to user local datetime.
function convertToLocalTime(date) {
    return new Date(Date.parse(date.concat("Z")));
}