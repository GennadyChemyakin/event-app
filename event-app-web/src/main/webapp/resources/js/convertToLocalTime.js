/**
* Function that receives date as string in ISO 8601 format without timezone and returns it as Date with UTC timezone.
* @param {String} date - date in ISO 8601 format without timezone
*/
function convertToLocalTime(date) {
    return new Date(Date.parse(date.concat("Z")));
}