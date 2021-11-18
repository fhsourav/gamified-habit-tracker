import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Test {

	public static void main(String[] args) throws ParseException {
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();
		System.out.println(date);
		calendar.add(calendar.DAY_OF_MONTH, -7);
		Date date2 = calendar.getTime();
		System.out.println(date2);
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTime());
		StreakUpdater su = new StreakUpdater(5, 15, date2);
		int streak = su.updateStreak();
		System.out.println(streak);
	}

}
