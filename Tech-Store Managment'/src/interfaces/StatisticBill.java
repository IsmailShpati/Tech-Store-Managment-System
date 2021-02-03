package interfaces;

import java.time.LocalDateTime;

public interface StatisticBill {
	LocalDateTime getDate();
	double getTotal();
}
