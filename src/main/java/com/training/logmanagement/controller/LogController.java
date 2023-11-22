package com.training.logmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.training.logmanagement.User;
import com.training.logmanagement.UserRepository;
import com.training.logmanagement.bean.Employee;
import com.training.logmanagement.repo.EmployeeRepo;
import com.training.logmanagement.service.LogService;
import com.training.logmanagement.service.LogServiceImplimentation;

@Controller
public class LogController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private LogServiceImplimentation logService;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	String emptyerror = null;
	
	List<Employee> empList = new ArrayList<>();
	ModelAndView mv = new ModelAndView();
	Employee empNew = new Employee();
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}

	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
		return "register_success";
	}

	@GetMapping("home")
	public ModelAndView viewHomePage(Model model) {
		mv.setViewName("home");
		employeeRepo.findAll().forEach(empList::add);
		mv.addObject("empList", empList);
		return mv;
	}

	@GetMapping("/employee")
	public ModelAndView listemployee(Model model) {
		emptyerror = null;
		mv.addObject(empNew);
		mv.setViewName("employee");
		employeeRepo.findAll().forEach(empList::add);
		mv.addObject("empList", empList);
		return mv;
	}

	// add empistration
	@RequestMapping(value = "/addemp", params = "add")
	public ModelAndView addemp(Employee emp) {
		if (emp.getEmployeeId() == null && !emp.getEmployeeName().isEmpty() && emp.getAge() != null
				&& !emp.getDesignation().isEmpty()) {
			while (true) {
				if (!emp.getEmployeeName().matches("[a-zA-Z]+")) {
					emptyerror = "enter a valid first name";
					mv.addObject("obj", emp);
					mv.addObject("emptyerror", emptyerror);
					mv.setViewName("employee");
					break;
				} else if (!emp.getDesignation().matches("[a-zA-Z ]+")) {
					emptyerror = "enter a valid Designation";
					mv.addObject("obj", emp);
					mv.addObject("emptyerror", emptyerror);
					mv.setViewName("employee");
					break;
				} else {
					employeeRepo.save(emp);

					mv.setViewName("employee");
					emptyerror = null;
					break;
				}
			}
		} else {
			emptyerror = "Field is empty";
			mv.addObject("obj", emp);
			mv.addObject("emptyerror", emptyerror);
			mv.setViewName("employee");
		}
		employeeRepo.findAll().forEach(empList::add);
		System.out.println();
		mv.addObject("empList", empList);
		return mv;
	}

	// serach empistration
	@RequestMapping(value = "/addemp", params = "search")
	public ModelAndView searchemp(Employee emp) {
		try {
			if (String.valueOf(emp.getEmployeeId()).matches("[0-9]+")) {
				if (emp.getEmployeeId() != null && emp.getEmployeeName().isEmpty() && emp.getAge() == null
						&& emp.getDesignation().isEmpty()) {
					Employee empNew = employeeRepo.findByEmployeeId(emp.getEmployeeId());
					System.out.println("idsearch" + empNew);
					mv.addObject("obj", empNew);
					mv.setViewName("employee");
					emptyerror = null;
					if (empNew == null) {
						emptyerror = "Id Not found ";
						mv.addObject("emptyerror", emptyerror);
					}

				} else {
					emptyerror = "Only Enter the id to be searched";
					mv.addObject("emptyerror", emptyerror);

				}
			} else {
				emptyerror = "Id must be number";
				mv.addObject("emptyerror", emptyerror);
			}
		} catch (Exception e) {
			emptyerror = "Id must be number";

			mv.addObject("emptyerror", emptyerror);
		}
		mv.setViewName("employee");
		empList=logService.findAll();
		mv.addObject("empList", empList);
		
		return mv;
	}

	// update empistration
	@RequestMapping(value = "/addemp", params = "update")
	public ModelAndView updateemp(Employee emp) {
		if (emp.getEmployeeId() != null && !emp.getEmployeeName().isEmpty() && emp.getAge() != null
				&& !emp.getDesignation().isEmpty()) {
			while (true) {
				if (!emp.getEmployeeName().matches("[a-zA-Z]+")) {
					emptyerror = "enter a valid first name";
					mv.addObject("obj", emp);
					mv.addObject("emptyerror", emptyerror);
					mv.setViewName("employee");
					break;
				 
				} else {
					employeeRepo.save(emp);
					mv.setViewName("employee");
					emptyerror = null;
					break;
				}
			}

		} else {
			emptyerror = "Field is empty";
			mv.addObject("emptyerror", emptyerror);
			mv.addObject("obj", emp);
			mv.setViewName("employee");

		}
		empList=logService.findAll();
		mv.addObject("empList", empList);
		return mv;
	}

	// delete empistration
	@RequestMapping(value = "/delete_user", method = RequestMethod.GET)
	public ModelAndView handleDeleteUser(@RequestParam(name = "employeeId") String employeeId) {
		employeeRepo.deleteById(Integer.parseInt(employeeId));
		empList=logService.findAll();
		mv.addObject("empList", empList);
		mv.setViewName("employee");
		return mv;
	}

	@GetMapping("entry")
	public ModelAndView entry(Model model) {
		mv.setViewName("entry");

		return mv;
	}

	@RequestMapping(value = "/entrysubmit", params = "submit")
	public ModelAndView entrySubmit(Employee emp) {
		try {
			if (String.valueOf(emp.getEmployeeId()).matches("[0-9]+")) {
				if (emp.getEmployeeId() != null) {
					Employee empNew = employeeRepo.findByEmployeeId(emp.getEmployeeId());
					System.out.println("idsearch" + empNew);
					// mv.addObject("obj", empNew);

					emptyerror = null;
					if (empNew == null) {
						emptyerror = "Id Not found ";
						mv.addObject("emptyerror", emptyerror);
						mv.setViewName("entry");

					} else if(empNew.getStatus().equalsIgnoreCase("IN")) {
						emptyerror = "EMPLOYEE IS ALREADY IN ";
						mv.addObject("emptyerror", emptyerror);
						mv.setViewName("entry");
					}
					else {
						empNew.setStatus("IN");
						employeeRepo.save(empNew);
						mv.setViewName("home");

					}

				} else {
					emptyerror = "dont enter null value";
					mv.addObject("emptyerror", emptyerror);
					mv.setViewName("entry");

				}
			} else {
				emptyerror = "Id must be number";
				mv.addObject("emptyerror", emptyerror);
				mv.setViewName("entry");

			}
		} catch (Exception e) {
			emptyerror = "Id must be number";

			mv.addObject("emptyerror", emptyerror);
			mv.setViewName("entry");

		}

		empList=logService.findAll();
		mv.addObject("empList", empList);
		return mv;
	}

	@GetMapping("exit")
	public ModelAndView exit(Model model) {
		mv.setViewName("exit");

		return mv;
	}

	@RequestMapping(value = "/exitsubmit", params = "submit")
	public ModelAndView exitSubmit(Employee emp) {
		
		
		try {
			if (String.valueOf(emp.getEmployeeId()).matches("[0-9]+")) {
				if (emp.getEmployeeId() != null) {
					Employee empNew = employeeRepo.findByEmployeeId(emp.getEmployeeId());
					System.out.println("idsearch" + empNew);
					// mv.addObject("obj", empNew);

					emptyerror = null;
					if (empNew == null) {
						emptyerror = "Id Not found ";
						mv.addObject("emptyerror", emptyerror);
						mv.setViewName("exit");

					} else if(empNew.getStatus().equalsIgnoreCase("OUT")) {
						emptyerror = "EMPLOYEE IS ALREADY OUT ";
						mv.addObject("emptyerror", emptyerror);
						mv.setViewName("exit");
					}
					else {
						empNew.setStatus("OUT");
						employeeRepo.save(empNew);
						mv.setViewName("home");

					}

				} else {
					emptyerror = "dont enter null value";
					mv.addObject("emptyerror", emptyerror);
					mv.setViewName("exit");

				}
			} else {
				emptyerror = "Id must be number";
				mv.addObject("emptyerror", emptyerror);
				mv.setViewName("exit");

			}
		} catch (Exception e) {
			emptyerror = "Id must be number";

			mv.addObject("emptyerror", emptyerror);
			mv.setViewName("exit");

		}

		empList=logService.findAll();
		mv.addObject("empList", empList);
		return mv;
	}
	
	@GetMapping("backhome")
	public ModelAndView backHomePage(Model model) {
		mv.setViewName("home");
		
		
		empList=logService.findAll();
		
		mv.addObject("empList", empList);
		return mv;
	}
	
	@RequestMapping(value = "/searchkey", params = "submit")
	public ModelAndView searchkey(@RequestParam(name = "key") String key) {
		System.out.println(key);
		
		mv.setViewName("home");
		mv.addObject("key",key);
		empList=logService.findAllEmployeeByKey(key);
		mv.addObject("empList", empList);
		return mv;
	}
	
}
