
public class MiniProject {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java MiniProject DOB=DD-MM-YYYY or AGE=20-05-15 ReferenceDate DateFormat Delimiter");
            return;
        }

        String dobOrAgeInput = args[0]; // Either "DOB=05-08-2005" or "AGE=20-05-15"
        String referenceDateInput = args[1]; // e.g., "07-11-2024"
        String dateFormat = args[2]; // e.g., "DDdlcMMdlcYYYY"
        String delimiter = args[3]; // e.g., "-"

        try
         {
            int[] refDateParts = parseDate(referenceDateInput, dateFormat, delimiter);

            if (dobOrAgeInput.startsWith("DOB=")) 
            {
                String dobInput = dobOrAgeInput.split("=")[1];
                int[] dobParts = parseDate(dobInput, dateFormat, delimiter);
                calculateAndPrintAge(dobParts, refDateParts);
            } 
            else if (dobOrAgeInput.startsWith("AGE=")) 
            {
                String ageInput = dobOrAgeInput.split("=")[1];
                int[] ageParts = parseAge(ageInput, delimiter);
                calculateAndPrintDOB(ageParts, refDateParts);
            } 
            else 
            {
                System.out.println("Invalid input format. Use DOB= or AGE= as the first argument.");
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void calculateAndPrintAge(int[] dob, int[] refDate) {
        int ageYears = refDate[2] - dob[2];
        int ageMonths = refDate[1] - dob[1];
        int ageDays = refDate[0] - dob[0];

        if (ageDays < 0) 
        {
            ageDays += daysInMonth(refDate[1] - 1, refDate[2]);
            ageMonths -= 1;
        }

        if (ageMonths < 0) 
        {
            ageMonths += 12;
            ageYears -= 1;
        }

        System.out.println("Age is: " + ageYears + " years, " + ageMonths + " months, " + ageDays + " days.");
    }

    private static void calculateAndPrintDOB(int[] age, int[] refDate) 
    {
        int dobYear = refDate[2] - age[0];
        int dobMonth = refDate[1] - age[1];
        int dobDay = refDate[0] - age[2];

        if (dobDay <= 0) 
        {
            dobMonth -= 1;
            dobDay += daysInMonth(dobMonth == 0 ? 12 : dobMonth, dobYear);
        }

        if (dobMonth <= 0) 
        {
            dobMonth += 12;
            dobYear -= 1;
        }

        System.out.println("Date of Birth is: " + dobDay + "-" + dobMonth + "-" + dobYear);
    }

    private static int[] parseDate(String date, String format, String delimiter) throws Exception 
    {
        String[] dateParts = date.split(delimiter);
        int day = 0, month = 0, year = 0;

        if (format.equals("DDdlcMMdlcYYYY")) 
        {
            day = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        } 
        else if (format.equals("YYYYdlcMMdlcDD"))
         {
            year = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            day = Integer.parseInt(dateParts[2]);
        } 
        else if (format.equals("MMdlcDDdlcYYYY"))
         {
            month = Integer.parseInt(dateParts[0]);
            day = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        } 
        else 
        {
            throw new Exception("Invalid date format.");
        }

        if (!isValidDate(day, month, year)) 
        {
            throw new Exception("Invalid date values.");
        }

        return new int[]{day, month, year};
    }

    private static int[] parseAge(String age, String delimiter) throws Exception 
    {
        String[] ageParts = age.split(delimiter);
        int years = Integer.parseInt(ageParts[0]);
        int months = Integer.parseInt(ageParts[1]);
        int days = Integer.parseInt(ageParts[2]);

        if (years < 0 || months < 0 || months > 11 || days < 0 || days > 30) 
        {
            throw new Exception("Invalid age values.");
        }

        return new int[]{years, months, days};
    }

    private static boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > 12 || day < 1) return false;
        return day <= daysInMonth(month, year);
    }

    private static int daysInMonth(int month, int year) {
        switch (month) {
            case 4: case 6: case 9: case 11: return 30;
            case 2: return (isLeapYear(year)) ? 29 : 28;
            default: return 31;
        }
    }

    private static boolean isLeapYear(int year)
     {
        if (year % 4 != 0) return false;
        if (year % 100 == 0 && year % 400 != 0) return false;
        return true;
    }
}
