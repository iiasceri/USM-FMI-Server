package fmi.usm.md.mvc.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Table(name = "groups")
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private int id;

    @Column(name = "name")
    private String name;

//    @Column(name = "year")
//    private int year;

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<User> userList;
}
