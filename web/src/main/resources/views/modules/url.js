var apiHost = "http://";
var ip_addr = document.location.hostname;
if(ip_addr == "192.168.31.113"){
    apiHost += ip_addr + ":7020"
}else if(ip_addr == "192.168.31.198"){
    apiHost += ip_addr + ":6020"
}else if(ip_addr == "62.234.156.67"){
    apiHost += ip_addr;
}else{
    apiHost += "192.168.31.198:6020";
}