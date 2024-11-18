package fateczl.docenteapp.model;

import fateczl.csvdb.annotations.CsvColumn;

public class Subject {

	@CsvColumn(columnIndex = "0", columnName = "Id", primaryKey = true)
	private final Integer id;

	@CsvColumn(columnIndex = "1", columnName = "Code")
	private final String code;
	//asdf
	@CsvColumn(columnIndex = "2", columnName = "Name")
	private final String name;

	@CsvColumn(columnIndex = "3", columnName = "Course Id")
	private final Integer courseId;
	
	@CsvColumn(columnIndex = "4", columnName = "Day")
	private final String day;
	
	@CsvColumn(columnIndex = "5", columnName = "Hours Per Day")
	private final String hoursPerDay;
	
	@CsvColumn(columnIndex = "6", columnName = "Start Time")
	private final String startTime;

	public Subject() {
		this.id = null;
		this.code = null;
		this.name = null;
		this.courseId = null;
		this.day = null;
		this.hoursPerDay = null;
		this.startTime = null;
	}

	Subject(Builder builder) {
		this.id = builder.id;
		this.code = builder.code;
		this.name = builder.name;
		this.courseId = builder.courseId;
		this.day = builder.day;
		this.hoursPerDay = builder.hoursPerDay;
		this.startTime = builder.startTime;
	}

	public Integer getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Integer getCourseId() {
		return courseId;
	}
	public String getDay() {
		return day;
	}
	public String getHoursPerDay() {
		return hoursPerDay;
	}
	public String getStartTime() {
		return startTime;
	}

	@Override
	public String toString() {
		return "Course{" + "id=" + id + ", code='" + code + '\'' + ", name='" + name + '\'' + ", courseId='"
				+ courseId + '\'' + '}';
	}

	/**
	 * Builder class for constructing Course objects.
	 */
	public static class Builder {
		private Integer id;
		private String code;
		private String name;
		private Integer courseId;
		private String day;
		private String hoursPerDay;
		private String startTime;

		/**
		 * Sets the id for the course.
		 *
		 * @param id the id to set
		 * @return the Builder instance for method chaining
		 */
		public Builder id(Integer id) {
			this.id = id;
			return this;
		}

		/**
		 * Sets the code for the course.
		 *
		 * @param code the code to set
		 * @return the Builder instance for method chaining
		 */
		public Builder code(String code) {
			this.code = code;
			return this;
		}

		/**
		 * Sets the name of the course.
		 *
		 * @param name the name of the course
		 * @return the Builder instance for chaining
		 */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/**
		 * Sets the name of the course.
		 *
		 * @param name the name of the course
		 * @return the Builder instance for chaining
		 */
		public Builder courseId(Integer courseId) {
			this.courseId = courseId;
			return this;
		}

		public Builder day(String day) {
			this.day = day;
			return this;
		}
		
		public Builder hoursPerDay(String hoursPerDay) {
			this.hoursPerDay = hoursPerDay;
			return this;
		}
		
		public Builder startTime(String startTime) {
			this.startTime = startTime;
			return this;
		}
		
		/**
		 * Builds and returns a new Subject instance using the current state of the
		 * builder.
		 *
		 * @return a new Subject instance
		 */
		
		public Subject build() {
			return new Subject(this);
		}
		
	}

}
