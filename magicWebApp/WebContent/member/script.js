function check_on(){
	if(document.reg_frm.mem_uid.value.length==0){
		alert("���̵� ���ּ���");
		reg_frm.mem_uid.focus();
		return;
	}
	
	if(document.reg_frm.mem_uid.value.length < 4){
		alert("���̵�� 4�����̻��̾�� �մϴ�.");
		reg_frm.mem_uid.focus();
		return;
	}
	
	if(document.reg_frm.mem_pwd.value == ""){
		alert("�н������ �ݵ�� �Է��ؾ� �մϴ�.");
		reg_frm.mem_pwd.focus();
		return;
	}
	
	if(document.reg_frm.mem_pwd.value != document.reg_frm.pwd_check.value){
		alert("�н����尡 ��ġ���� �ʽ��ϴ�.");
		reg_frm.mem_pwd.focus();
		return;
	}
	
	if(document.reg_frm.mem_name.value.length==0){
		alert("�̸��� ���ּ���");
		reg_frm.mem_name.focus();
		return;
	}
	
	if(document.reg_frm.mem_email.value.length==0){
		alert("Email�� ���ּ���");
		reg_frm.mem_email.focus();
		return;
	}
	document.reg_frm.submit();
}

function update_check_ok(){
	if(document.reg_frm.mem_pwd.value == ""){
		alert("�н������ �ݵ�� �Է��ؾ� �մϴ�.");
		reg_frm.mem_pwd.focus();
		return;
	}
	if(document.reg_frm.mem_pwd.value != document.reg_frm.pwd_check.value){
		alert("�н����尡 ��ġ���� �ʽ��ϴ�.");
		reg_frm.mem_pwd.focus();
		return;
	}
	if(document.reg_frm.mem_email.value.length==0){
		alert("Email�� ���ּ���");
		reg_frm.mem_email.focus();
		return;
	}
	document.reg_frm.submit();
}