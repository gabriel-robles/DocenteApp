package fateczl.docenteapp.models;

import fateczl.csvdb.annotations.CsvColumn;

public class Subject {
	@CsvColumn(columnIndex = "0", columnName = "Id", primaryKey = true)
	private final Integer id;

	@CsvColumn(columnIndex = "1", columnName = "Process")
	private final String process;

	@CsvColumn(columnIndex = "2", columnName = "Code")
	private final String code;

	@CsvColumn(columnIndex = "3", columnName = "Name")
	private final String name;

	@CsvColumn(columnIndex = "4", columnName = "CourseCode")
	private final String courseCode;
	
	@CsvColumn(columnIndex = "5", columnName = "Day")
	private final String day;
	
	@CsvColumn(columnIndex = "6", columnName = "StartTime")
	private final String startTime;

	@CsvColumn(columnIndex = "7", columnName = "HoursPerDay")
	private final String hoursPerDay;
	
	public Subject() {
		this.id = null;
		this.process = null;
		this.code = null;
		this.name = null;
		this.courseCode = null;
		this.day = null;
		this.hoursPerDay = null;
		this.startTime = null;
	}

	Subject(Builder builder) {
		this.id = builder.id;
		this.process = builder.process;
		this.code = builder.code;
		this.name = builder.name;
		this.courseCode = builder.courseCode;
		this.day = builder.day;
		this.hoursPerDay = builder.hoursPerDay;
		this.startTime = builder.startTime;
	}

	public Integer getId() {
		return id;
	}

	public String getProcess() {
		return process;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getCourseCode() {
		return courseCode;
	}
	public String getDay() {
		return day;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getHoursPerDay() {
		return hoursPerDay;
	}

	@Override
	public String toString() {
		return "Course{"
				+ "id=" + id
				+ ", process='" + process + '\''
				+ ", code='" + code + '\''
				+ ", name='" + name + '\''
				+ ", courseCode='"+ courseCode + '\''
				+ ", day='" + day + '\''
				+ ", startTime='" + startTime + '\''
				+ ", hoursPerDay='" + hoursPerDay
				+ '}';
	}

	/**
	 * Builder class for constructing instances of {@link Subject}.
	 */
	public static class Builder {
		private Integer id;
		private String process;
		private String code;
		private String name;
		private String courseCode;
		private String day;
		private String hoursPerDay;
		private String startTime;

		/**
		 * Sets the id of the subject.
		 *
		 * @param id the id to set
		 * @return the Builder instance for method chaining
		 */
		public Builder id(Integer id) {
			this.id = id;
			return this;
		}

		/**
		 * Sets the process for the Subject.
		 *
		 * @param process the process to set
		 * @return the Builder instance for chaining
		 */
		public Builder process(String process) {
			this.process = process;
			return this;
		}

		/**
		 * Sets the code for the Subject.
		 *
		 * @param code the code to set
		 * @return the Builder instance for chaining
		 */
		public Builder code(String code) {
			this.code = code;
			return this;
		}

		/**
		 * Sets the name of the subject.
		 *
		 * @param name the name to set
		 * @return the builder instance
		 */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/**
		 * Sets the course code for the subject.
		 *
		 * @param courseCode the course code to set
		 * @return the Builder instance for chaining
		 */
		public Builder courseCode(String courseCode) {
			this.courseCode = courseCode;
			return this;
		}

		/**
		 * Sets the day for the subject.
		 *
		 * @param day the day to set
		 * @return the Builder instance for chaining
		 */
		public Builder day(String day) {
			this.day = day;
			return this;
		}
		
		/**
		 * Sets the start time for the subject.
		 *
		 * @param startTime the start time of the subject in a specific format (e.g., "HH:mm").
		 * @return the Builder instance for method chaining.
		 */
		public Builder startTime(String startTime) {
			this.startTime = startTime;
			return this;
		}

		/**
		 * Sets the hours per day for the subject.
		 *
		 * @param hoursPerDay the hours per day to be set
		 * @return the Builder instance for method chaining
		 */
		public Builder hoursPerDay(String hoursPerDay) {
			this.hoursPerDay = hoursPerDay;
			return this;
		}		
		
		/**
		 * Builds and returns a new Subject instance using the current state of the builder.
		 *
		 * @return a new Subject instance
		 */
		public Subject build() {
			return new Subject(this);
		}		
	}
}
