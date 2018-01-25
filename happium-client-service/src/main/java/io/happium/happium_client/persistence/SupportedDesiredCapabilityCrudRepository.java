package io.happium.happium_client.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Data Access Object for the SupportedDesiredCapability object table
 *
 * <p>
 *     SupportedDesiredCapability Objects are stored with their name as
 *     their ID - this is done since there are only a finite number of
 *     DesiredCapabilities to begin with. Additionally, the majority of
 *     the provided DesiredCapabilities are for specific use-cases, many
 *     of which are outside the scope of regular Appium Sessions.
 * <p>
 *     Happium should only ever store references to the bare minimum
 *     required supported DesiredCapabilities. The absolute minimum is
 *     provided for you in resources/supported_desired_capabilities.json.
 *     It's recommended that, should you need to add support for more
 *     DesiredCapabilities, that you DO NOT do so in the provided JSON.
 *     Instead, it's better to use Happium infrastructure to programmatically
 *     add more capabilities to support as you need them. The JSON should
 *     be preserved so you always know the bare minimum of what's required.
 */
@Repository
@Transactional
public interface SupportedDesiredCapabilityCrudRepository extends CrudRepository<SupportedDesiredCapability, String> {

}
