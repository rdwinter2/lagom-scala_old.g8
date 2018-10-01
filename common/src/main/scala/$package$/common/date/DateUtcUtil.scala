package $package$.common.date

import org.joda.time.{DateTime, DateTimeZone}

object DateUtcUtil {
  def now() = DateTime.now(DateTimeZone.UTC)
}
