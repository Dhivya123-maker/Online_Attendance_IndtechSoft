package EmployeeAttendance;

public class EmployeeAttendanceModel {
    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public EmployeeAttendanceModel(String s_no) {
        this.s_no = s_no;
    }

    String s_no;
    String name;
    String user_role;
    String login_time;
    String logout_time;
    String lunch_start;

    public String getBreak_start() {
        return break_start;
    }

    public void setBreak_start(String break_start) {
        this.break_start = break_start;
    }

    public String getBreak_end() {
        return break_end;
    }

    public void setBreak_end(String break_end) {
        this.break_end = break_end;
    }

    public String getPermission_start() {
        return permission_start;
    }

    public void setPermission_start(String permission_start) {
        this.permission_start = permission_start;
    }

    public String getPermission_end() {
        return permission_end;
    }

    public void setPermission_end(String permission_end) {
        this.permission_end = permission_end;
    }

    public EmployeeAttendanceModel(String break_start, String break_end, String permission_start, String permission_end) {
        this.break_start = break_start;
        this.break_end = break_end;
        this.permission_start = permission_start;
        this.permission_end = permission_end;
    }

    String break_start;
    String break_end;
    String permission_start;
    String permission_end;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getLogout_time() {
        return logout_time;
    }

    public void setLogout_time(String logout_time) {
        this.logout_time = logout_time;
    }

    public String getLunch_start() {
        return lunch_start;
    }

    public void setLunch_start(String lunch_start) {
        this.lunch_start = lunch_start;
    }

    public String getLunch_end() {
        return lunch_end;
    }

    public void setLunch_end(String lunch_end) {
        this.lunch_end = lunch_end;
    }

    public String getTea_start() {
        return tea_start;
    }

    public void setTea_start(String tea_start) {
        this.tea_start = tea_start;
    }

    public String getTea_end() {
        return tea_end;
    }

    public void setTea_end(String tea_end) {
        this.tea_end = tea_end;
    }

    public String getTravel_start() {
        return travel_start;
    }

    public void setTravel_start(String travel_start) {
        this.travel_start = travel_start;
    }

    public String getTravel_end() {
        return travel_end;
    }

    public void setTravel_end(String travel_end) {
        this.travel_end = travel_end;
    }

    public String getMeeting_start() {
        return meeting_start;
    }

    public void setMeeting_start(String meeting_start) {
        this.meeting_start = meeting_start;
    }

    public String getMeeting_end() {
        return meeting_end;
    }

    public void setMeeting_end(String meeting_end) {
        this.meeting_end = meeting_end;
    }

    String lunch_end;
    String tea_start;
    String tea_end;

    public EmployeeAttendanceModel(String name, String user_role, String login_time, String logout_time, String lunch_start, String lunch_end, String tea_start, String tea_end, String travel_start, String travel_end, String meeting_start, String meeting_end) {
        this.name = name;
        this.user_role = user_role;
        this.login_time = login_time;
        this.logout_time = logout_time;
        this.lunch_start = lunch_start;
        this.lunch_end = lunch_end;
        this.tea_start = tea_start;
        this.tea_end = tea_end;
        this.travel_start = travel_start;
        this.travel_end = travel_end;
        this.meeting_start = meeting_start;
        this.meeting_end = meeting_end;
    }

    String travel_start;
    String travel_end;
    String meeting_start;
    String meeting_end;


    public EmployeeAttendanceModel(){}
}
