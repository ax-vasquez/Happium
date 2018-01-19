package io.happium.happium_core.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Entity to represent a Selenium Session object that
 * is currently "active" (meaning it is currently being
 * executed on a hosting WebDriver instance).
 *
 * <p>
 *     This class is only used in populating an "active sessions"
 *     table that will be accessible via the Happium dashboard.
 *     As Sessions are made active, they are added to this table.
 *     Once a Session is complete, it is removed from this table.
 */
@Entity
@Transactional
public class ActiveSeleniumServerSession {

    /**
     * This ID is unique to the ActiveSeleniumServerSession object
     * and should be used in conjunction with the underlying HappiumSession
     * ID in order to generate a chronological history of session
     * runs
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "active_session_id", nullable = false)
    @Getter @Setter private Long activeSeleniumSessionId;

    /**
     * This ID is auto-generated within the HappiumSession class
     * on instantiation - use the HappiumSession's getHappiumSessionId()
     * method to populate this field
     */
    @Column(name = "happium_session_id", nullable = false)
    @Getter @Setter private Long baseHappiumSessionId;

}
