function check_ok() {
  if(document.form.b_name.value.length==0) {
    alert("�̸��� �Է��ϼ���.");
    form.b_name.focus();
    return false;
  }
  if(document.form.b_title.value.length==0) {
    alert("������ �Է��ϼ���.");
    form.b_title.focus();
    return false;
  }
  if(document.form.b_content.value.length==0) {
    alert("������ �Է��ϼ���.");
    form.b_content.focus();
    return false;
  }
  if(document.form.b_pwd.value.length==0) {
    alert("��й�ȣ�� �Է¼���.");
    form.b_pwd.focus();
    return false;
  }  
  document.form.submit();
}
function delete_ok(){
  if(document.form.b_pwd.value.length==0) {
    alert("��й�ȣ�� �Է¼���.");
    form.b_pwd.focus();
    return false;
  }  
  document.form.submit();
}