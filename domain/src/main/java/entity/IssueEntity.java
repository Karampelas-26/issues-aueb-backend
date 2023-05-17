package entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name= "Issue")
public class IssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private String id;
    @Column(name="title")
    private String title;
    @Column(name="site")
    private String site;
    @Column(name="building")
    private Building building;
}
