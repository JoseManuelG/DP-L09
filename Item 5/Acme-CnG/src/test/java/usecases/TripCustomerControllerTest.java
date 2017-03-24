
package usecases;

import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@WebAppConfiguration
@EnableWebMvc
@Transactional
public class TripCustomerControllerTest extends AbstractTest {

	private MockMvc					mockMvc;

	@Autowired
	private WebApplicationContext	wac;


	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@SuppressWarnings({
		"unchecked", "deprecation"
	})
	@Test
	public void testListAllOffersAsCustomer1() throws Exception {
		this.authenticate("customer1");
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/trip/customer/list/my/offers.do"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("trip/list/my/offers"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("trip/list/my/offers"))
			.andExpect(MockMvcResultMatchers.model().attribute("trips", Matchers.hasSize(3)))
			.andExpect(MockMvcResultMatchers.model().attribute("requestURI", Matchers.hasToString("trip/customer/list/my/offers.do")))
			.andExpect(
				MockMvcResultMatchers.model().attribute(
					"trips",
					Matchers.hasItem(Matchers.allOf(Matchers.hasProperty("id", Matchers.is(Integer.class)), Matchers.hasProperty("version"), Matchers.hasProperty("title"), Matchers.hasProperty("description"), Matchers.hasProperty("departureTime"),
						Matchers.hasProperty("origin"), Matchers.hasProperty("destination"), Matchers.hasProperty("originLat"), Matchers.hasProperty("originLon"), Matchers.hasProperty("destinationLat"), Matchers.hasProperty("destinationLon"),
						Matchers.hasProperty("type"), Matchers.hasProperty("banned"), Matchers.hasProperty("customer")))));

	}

	@Test
	public void testSearchAsCustomer1() throws Exception {
		this.authenticate("customer1");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/trip/customer/search/offers.do?keyword=Barcelona")).andExpect(MockMvcResultMatchers.forwardedUrl("trip/search/list")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("trips", Matchers.hasSize(0)));
	}

	@Test
	public void testCreateOfferAndLaterCheckAsCustomer1() throws Exception {
		this.authenticate("customer1");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/trip/customer/create/offer.do")).andExpect(MockMvcResultMatchers.forwardedUrl("trip/create/offer")).andExpect(MockMvcResultMatchers.status().isOk());
		this.mockMvc.perform(
			MockMvcRequestBuilders.post("/trip/customer/edit.do").param("save", "save").param("title", "Lets go to Barcelona!").param("description", "We are going from Seville to Barcelona").param("departureTime", "25/08/2017").param("origin", "Seville")
				.param("destination", "Barcelona").param("type", "OFFER")).andExpect(MockMvcResultMatchers.redirectedUrl("/trip/customer/list/my/offers.do"));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/trip/customer/list/my/offers.do")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("trip/list/my/offers"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("trip/list/my/offers")).andExpect(MockMvcResultMatchers.model().attribute("trips", Matchers.hasSize(4)));
	}
}
