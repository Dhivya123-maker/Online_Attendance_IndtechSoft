package EmployeeInfo;

public class EmployeeInfoModel {
    String s_no;
    String emp_code;
    String emp_name;
    String gender;

    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    String email;

    public EmployeeInfoModel(String s_no, String emp_code, String emp_name, String gender, String email, String mobile_no) {
        this.s_no = s_no;
        this.emp_code = emp_code;
        this.emp_name = emp_name;
        this.gender = gender;
        this.email = email;
        this.mobile_no = mobile_no;
    }

    String mobile_no;

    public EmployeeInfoModel(){}
}
