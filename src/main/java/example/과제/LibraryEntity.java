package example.과제;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "library")
@Data
public class LibraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    private String book_name;
    private String book_writer;
    private String book_publisher;
    private Date book_date;


}
