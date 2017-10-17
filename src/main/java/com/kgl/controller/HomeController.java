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
import com.kgl.models.Operadora;
import com.kgl.models.Role;
import com.kgl.models.StatusMovimentacao;
import com.kgl.models.User;
import com.kgl.repository.CorretorRepository;
import com.kgl.repository.UserRepository;
import com.kgl.webservices.ContratoRepository;
import com.kgl.webservices.MovimentacaoRepository;
import com.kgl.webservices.OperadoraRepository;

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
	
	@Autowired
	private OperadoraRepository operadoraRepository; 

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
			return new ModelAndView("index");
		} else {
			return conf.updatePassword(new User());
		}
		
	}
	@Transactional
	@ResponseBody
	@RequestMapping("/prepararAmbiente")
	public ModelAndView prepararAmbiente() {
		operadoraRepository.save(new Operadora("ALLIAZ SEGUROS"));
		operadoraRepository.save(new Operadora("AMEPLAN"));
		operadoraRepository.save(new Operadora("AMIL"));
		operadoraRepository.save(new Operadora("BIO SAÚDE"));
		operadoraRepository.save(new Operadora("BIO VIDA"));
		operadoraRepository.save(new Operadora("BRADESCO SAÚDE"));
		operadoraRepository.save(new Operadora("CAIXA SEGURO SAÚDE"));
		operadoraRepository.save(new Operadora("CAIXA/ADMIX"));
		operadoraRepository.save(new Operadora("CORPORE GNDI"));
		operadoraRepository.save(new Operadora("GARANTIA SAÚDE"));
		operadoraRepository.save(new Operadora("GARANTIA SAÚDE ADVENTISTA"));
		operadoraRepository.save(new Operadora("GNDI - NOTREDAME INTERMEDICA"));
		operadoraRepository.save(new Operadora("GREEN LINE"));
		operadoraRepository.save(new Operadora("GREEN LINE"));
		operadoraRepository.save(new Operadora("MARITIMA"));
		operadoraRepository.save(new Operadora("MEDICAL HEALTH"));
		operadoraRepository.save(new Operadora("NEXT SEISA"));
		operadoraRepository.save(new Operadora("NEXT SEISA"));
		operadoraRepository.save(new Operadora("OESTE"));
		operadoraRepository.save(new Operadora("PLENA"));
		operadoraRepository.save(new Operadora("PORTO SEGURO"));
		operadoraRepository.save(new Operadora("SANTA HELENA"));
		operadoraRepository.save(new Operadora("SÃO CRISTOVÃO"));
		operadoraRepository.save(new Operadora("SÃO ICHEL SAÚDE"));
		operadoraRepository.save(new Operadora("SUL AMÉRICA"));
		operadoraRepository.save(new Operadora("TRASMONTANO"));
		operadoraRepository.save(new Operadora("AMIL DENTAL"));
		operadoraRepository.save(new Operadora("BRADESCO DENTAL"));
		operadoraRepository.save(new Operadora("DENTALPAR"));
		operadoraRepository.save(new Operadora("DENTALPLUS"));
		operadoraRepository.save(new Operadora("ODONTOPREV"));
		operadoraRepository.save(new Operadora("PREVIDENT"));
		operadoraRepository.save(new Operadora("SUL AMÉRICA"));
		operadoraRepository.save(new Operadora("UNIODONTO"));
		operadoraRepository.save(new Operadora("CORPORE AMEPLAN"));
		operadoraRepository.save(new Operadora("CORPORE AMEPLAN MIDDLE"));
		operadoraRepository.save(new Operadora("CORPORE DEMAIS PRODUTOS"));
		operadoraRepository.save(new Operadora("QUALICORP"));
		operadoraRepository.save(new Operadora("AMEPLAN"));
		operadoraRepository.save(new Operadora("AMIL DENTAL"));
		operadoraRepository.save(new Operadora("BIOVIDA / BIOVIDA SENIOR"));
		operadoraRepository.save(new Operadora("GARANTIA SAÚDE")); 
		operadoraRepository.save(new Operadora("GARANTIA SAÚDE ADVENTISTA"));
		operadoraRepository.save(new Operadora("GREENLINE SENIOR"));
		operadoraRepository.save(new Operadora("MEDIAL HEALTH"));
		operadoraRepository.save(new Operadora("NEXT SAÚDE"));
		operadoraRepository.save(new Operadora("OESTE SAÚDE"));
		operadoraRepository.save(new Operadora("PLENA"));
		operadoraRepository.save(new Operadora("PREVIDENT"));
		operadoraRepository.save(new Operadora("SÃO CRISTÓVÃO (ATÉ 58 ANOS) C/ COPARTICIPAÇÃO"));
		operadoraRepository.save(new Operadora("SÃO MICHEL SAÚDE"));
		return new ModelAndView("/index");
	}

}
