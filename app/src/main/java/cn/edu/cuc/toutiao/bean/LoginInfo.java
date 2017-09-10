package cn.edu.cuc.toutiao.bean;

/**
 * Created by jack on 2017/9/10.
 */

public class LoginInfo {
    /**
     * Mes :
     * is_success : 1
     * session : Px9UYSL0M07oSWF+ouHUmw2Z5b1yaCTKGZ6TXaVMstmelgezZl36IbE2HJ6C5gJs
     * ub : {"uid":"zhyannan@qq.com","phone":"","email":"zhyannan@qq.com","name":"","sex":"","university":"","school":"","major":"","education":""}
     */

    private String Mes;
    private int is_success;
    private String session;
//    private UbBean ub;

    public String getMes() {
        return Mes;
    }

    public void setMes(String Mes) {
        this.Mes = Mes;
    }

    public int getIs_success() {
        return is_success;
    }

    public void setIs_success(int is_success) {
        this.is_success = is_success;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

//    public UbBean getUb() {
//        return ub;
//    }

//    public void setUb(UbBean ub) {
//        this.ub = ub;
//    }

    public static class UbBean {
        /**
         * uid : zhyannan@qq.com
         * phone :
         * email : zhyannan@qq.com
         * name :
         * sex :
         * university :
         * school :
         * major :
         * education :
         */

        private String uid;
        private String phone;
        private String email;
        private String name;
        private String sex;
        private String university;
        private String school;
        private String major;
        private String education;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUniversity() {
            return university;
        }

        public void setUniversity(String university) {
            this.university = university;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }
    }
}
