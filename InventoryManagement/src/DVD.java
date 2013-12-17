import java.io.Serializable;
/**
 * DVD class
 * @author user
 *
 */
public class DVD implements Comparable<DVD>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String year;
	private String directors;
	private String description;
	private String genres;
	private String imdbRating;
	private String runtime;
	
	public DVD (){
		title = "na";
		year = "na";
		directors = "na";
		description = "na";
		genres = "na";
		imdbRating = "na";
		runtime = "na";
	}
	public DVD(String title, String year, String directors, String description, String genres, String imdbRating, String runtime){
		this.title = title;
		this.year = year;
		this.directors = directors;
		this.description = description;
		this.genres = genres;
		this.imdbRating = imdbRating;
		this.runtime = runtime;
	}
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getDirectors() {
		return directors;
	}


	public void setDirectors(String directors) {
		this.directors = directors;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getGenres() {
		return genres;
	}


	public void setGenres(String genres) {
		this.genres = genres;
	}


	public String getImdbRating() {
		return imdbRating;
	}


	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}


	public String getRuntime() {
		return runtime;
	}


	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}


	@Override
	public int compareTo(DVD d) {
		// TODO Auto-generated method stub
		return title.compareTo(d.title);
	}
	@Override
	public String toString(){
		
		return this.title +" ("+ this.year +")\n"
				+ this.runtime + " min - " + this.genres + " Ratings: "+this.imdbRating+"/10\n"
				+ "Director: " + this.directors +"\n"
				+ this.description +"\n";
		
		
	}

}
