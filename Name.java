public class Name {
    private String firstName;
    private String middleName;
    private String lastName;

    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }




    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getInitials() {
        StringBuilder initials = new StringBuilder();
        if (firstName != null && !firstName.isEmpty()) {
            initials.append(firstName.charAt(0));
        }
        if (middleName != null && !middleName.isEmpty()) {
            initials.append(middleName.charAt(0));
        }
        if (lastName != null && !lastName.isEmpty()) {
            initials.append(lastName.charAt(0));
        }
        return initials.toString();
    }

    public CharSequence getMiddleName() {
        return middleName;
    }

    public CharSequence getLastName() {
        return lastName;
    }
}

