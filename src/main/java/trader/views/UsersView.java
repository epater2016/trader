package trader.views;

import java.util.ArrayList;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import trader.models.User;
import trader.services.TraderUserService;

@Secured({"ROLE_ADMIN"})
@SpringView(name = "users")
@UIScope
public class UsersView extends VerticalLayout implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2671806438249999662L;

	private Grid grid;
	
	@Autowired
	private TraderUserService service;
	
	@PostConstruct
	protected void initialize() {
		setSizeFull();
		grid = new Grid();
		grid.setSizeFull();
		
		grid.addColumn("firstName", String.class);
		grid.addColumn("lastName", String.class);
		
		addComponent(grid);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		Iterable<User> users = service.getUsers();
		ArrayList<User> list = new ArrayList<User>();
		users.forEach(e -> list.add(e));
		grid.setContainerDataSource(new BeanItemContainer<User>(User.class,list));
	}

}
