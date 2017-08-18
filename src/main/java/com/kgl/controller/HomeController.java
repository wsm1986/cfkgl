package com.kgl.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Contrato;
import com.kgl.models.Corretor;
import com.kgl.models.GenerateHashPasswordUtil;
import com.kgl.models.Movimentacao;
import com.kgl.models.Role;
import com.kgl.models.StatusMovimentacao;
import com.kgl.models.User;
import com.kgl.repository.CorretorRepository;
import com.kgl.repository.UserRepository;
import com.kgl.webservices.ContratoRepository;
import com.kgl.webservices.MovimentacaoRepository;

@Controller
@Scope("session")
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovimentacaoRepository movRepository;

	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	ConfigurarUser conf;

	@Autowired
	private CorretorRepository corretorRep;

	@ResponseBody
	@RequestMapping("/index")
	public ModelAndView home(@AuthenticationPrincipal User user, HttpSession session) throws Exception {
		ModelAndView mvn = new ModelAndView("index");
		Boolean permissao = Boolean.FALSE;
		BigDecimal vlr = new BigDecimal(0);
		for (Role r : user.getRoles()) {
			if (r.getAuthority().equals("ROLE_ADMIN")) {
				permissao = Boolean.TRUE;
				break;

			} else if (r.getAuthority().equals("ROLE_USER")) {
				permissao = Boolean.FALSE;
				break;
			}
		}
		session.setAttribute("permissao", permissao);

		if (permissao) {
			List<Movimentacao> movs = (List<Movimentacao>) movRepository.findAll();
			for (Movimentacao movimentacao : movs) {
				if (movimentacao.getStatus().equals(StatusMovimentacao.AGUARDADO_PAGAMENTO)) {
					vlr = vlr.add(movimentacao.getLucro());

				}
			}
			session.setAttribute("nomeCorretor", "ADM - KGL");

		} else {
			Corretor corretor = corretorRep.findByEmail(user.getUserName());
			List<Contrato> contratos = contratoRepository.findByCorretor(corretor);
			for (Contrato contrato : contratos) {
				List<Movimentacao> movs = movRepository.findByContratoId(contrato.getId());
				for (Movimentacao movimentacao : movs) {
					if (movimentacao.getStatus().equals(StatusMovimentacao.AGUARDADO_PAGAMENTO)) {
						vlr = vlr.add(movimentacao.getValorCorretor());
					}
				}

			}
			session.setAttribute("nomeCorretor", corretor.getNome());
			session.setAttribute("emailCorretor", user.getUserName());

		}
		session.setAttribute("vlrCorretor", vlr);
		return mvn;
	}

	@Transactional
	@ResponseBody
	@RequestMapping("/url-magica-maluca-")
	public ModelAndView urlMagicaMaluca() throws Exception {
		User user;
		List<User> users = (List<User>) userRepository.findAll();
		if (users.size() == 0) {
			String password = GenerateHashPasswordUtil.generateHash("1234");
			List<Role> list = new ArrayList();
			Role role = new Role();
			role.setNome("ROLE_ADMIN");
			list.add(role);
			user = new User("kglbergamini@gmail.com", password, list);
			userRepository.save(user);
			return conf.updatePassword(user);
		} else {
			return conf.updatePassword(new User());
		}
	}

}

/*
 * 
 * 
 * INSERT INTO employee(name, last_name, email, phone, active) values ('Gustavo VIADO','Ponce','test@test.com','1234567890',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Bob','Marley','one@love.com','6483748590',false);
INSERT INTO employee(name, last_name, email, phone, active) values ('David','Gilmour','high@hopes.com','7648909831',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('John','Lennon','standby@me.com','7689485620',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Ozzy','Osbourne','children@grave.com','6483748590',false);
INSERT INTO employee(name, last_name, email, phone, active) values ('Jimmy','Page','stairway@heaven.com','7648909831',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Jimi','Hendrix','purple@haze.com','8754091236',false);
INSERT INTO employee(name, last_name, email, phone, active) values ('Sex','Pistols','save@queen.com','6729098761',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Jim','Morrison','riders@storm.com','8754091236',false);
INSERT INTO employee(name, last_name, email, phone, active) values ('Richard','Blackmore','highway@star.com','8754091236',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Jay','Kay','cosmic@girl.com','0926389871',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('David','Bowie','heroes@oneday.com','4338490981',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Bob','Dylan','knocking@doors.com','4338490981',false);
INSERT INTO employee(name, last_name, email, phone, active) values ('Manu','Chao','mala@vida.com','8923098753',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('The','Specials','ghost@thown.com','7590498573',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Jymmy','Cliff','see@clearly.com','4338490981',false);
INSERT INTO employee(name, last_name, email, phone, active) values ('The','Temptations','my@girl.com','7639864096',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Simon','Garfunkel','mr@robinson.com','8750987531',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('catch','22','takes@sometime.com','7098653427',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Janis','Joplin','cry@baby.com','6739087641',false);
INSERT INTO employee(name, last_name, email, phone, active) values ('Lou','Red','wild@side.com','6789045678',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Iggy','Pop','the@passenger.com','6934980751',true);
INSERT INTO employee(name, last_name, email, phone, active) values ('Dead','Kennedys','holiday@cambodia.com','2389096457',false);
INSERT INTO employee(name, last_name, email, phone, active) values ('The','Cure','dont@cry.com','8749340987',false);
INSERT INTO employee(name, last_name, email, phone, active) values ('WELL','Cure','dont@cry.com','8749340987',false);
INSERT INTO usuario (senha, user_name) values ('$2a$10$ROak1.PTNN2Nl/qL27d/uumTKtRa844KO9TDxqDsGSt31ZUKo0fbe', 'wsm1986@gmail.com');
INSERT INTO role (nome) values ('ROLE_ADMIN');
INSERT INTO usuario_roles (user_id, roles_id) values (1, 1);*/
