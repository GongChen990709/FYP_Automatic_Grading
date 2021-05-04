var flag=true;

function set() {
    var menu2=document.getElementById("menu2");
    if(flag){
        menu2.style.display="block";
        flag=false;
    }else{
        menu2.style.display="none";
        flag=true;
    }

}

function set1(){
    var menu2=document.getElementById("menu2");
    menu2.style.display="none";
    flag=true;
}