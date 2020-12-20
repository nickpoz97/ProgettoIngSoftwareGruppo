package GestioneServer;

import GestioneRuolo.Admin;
import GestioneRuolo.OperatoreSanitario;
import GestioneRuolo.Ruolo;
import GestioneRuolo.Segreteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private PersonRepository repository;

    @RequestMapping("/")
    public String index(Model model) {
        return "redirect:/login";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        List<Utente> data = new LinkedList<>();
        for (Utente p : repository.findAll()) {
            data.add(p);
        }
        model.addAttribute("people", data);
        return "list";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        String errore = "";
        model.addAttribute("Errore", errore);
        return "login";
    }

    /* --------------------------------------------------------------------------------------------------------
       Confirm serve per la gestione che collega la schermata di login a tutto il resto del programma,
       in essa vengono verificati che i campi (username e password non siano vuoti) e che siano effettivamente
       presenti nel database.
       --------------------------------------------------------------------------------------------------------

     */
    @RequestMapping("/confirm")
    public String create(
            @RequestParam(name = "Username", required = false) String Username,
            @RequestParam(name = "Password", required = false) String Password,
            @RequestParam(name = "Ruolo", required = false) String Role,
            @RequestParam(name = "Errore", required = false) String errore, Model model) {

            Utente u = new Utente(Username, Password, Role);
            if (Username.length() == 0 || Password.length() == 0) {
                if (Username.length() == 0) {
                    errore = "ATTENZIONE:USERNAME NON PUÒ ESSERE VUOTO!\n";
                }

                if (Password.length() == 0) {
                    errore += "La Password non può essere vuota";
                }
                model.addAttribute("Errore", errore);
                return "login";
            } else {
                Server server = new Server();
                if (server.isPresent(u)) {
                    switch(Role) {

                        case "admin": {
                            System.out.println("ciao");
                            Ruolo r = new Admin(Role);
                            model.addAttribute("Username",u.getUsername());
                            model.addAttribute("Password",u.getPassword());
                            model.addAttribute("Ruolo",u.getRuolo());
                            return r.getRuolo();
                        }
                        case "Operatore Sanitario": {
                            Ruolo r = new OperatoreSanitario(Role);
                            return r.getRuolo();
                        }
                        case "Segreteria": {
                            Ruolo r = new Segreteria(Role);
                            return r.getRuolo();
                        }
                    }
                }
                else {
                    errore = "Username o password  non corretta o non trovata.";
                    model.addAttribute("Errore", errore);
                    return "login";
                }
            }
            return "error";

    }
    /* ------------------------------------------------------------------------------------------
       La request mapping admin gestisce le funzionalità del manager in particolare indirizza alla pagina corretta
       l'operazione indicata dal manager.
       -------------------------------------------------------------------------------------------
    */
    @RequestMapping("admin")
    public String admin( @RequestParam(name = "Username", required = false) String Username,
                         @RequestParam(name = "Password", required = false) String Password,
                         @RequestParam(name = "Ruolo", required = false) String Ruolo,
                         @RequestParam(name = "Operation", required = false) String operation,Model model) {
        switch (operation) {
            case "NuovoUtente":  {
                model.addAttribute("Username",Username);
                model.addAttribute("Password", Password);
                model.addAttribute("Ruolo",Ruolo);
                return "insert";
            }
            case "GenerateReport":  {
                model.addAttribute("Username",Username);
                model.addAttribute("Password", Password);
                model.addAttribute("Ruolo",Ruolo);
                return "redirect:/list";
            }
            case "Statistical" : {
                    model.addAttribute("Username",Username);
                    model.addAttribute("Password", Password);
                    model.addAttribute("Ruolo",Ruolo);
                    return "redirect:/list";
                }
            }
            return "error";
        }





/*----------------------------------------------------------------------------------------------------------------------
    La insert serve per inserire un nuovo utente nel database, viene gestita tramite admin
------------------------------------------------------------------------------------------------------------------------
 */
    @RequestMapping("/insert")
  public String insert(@RequestParam(name = "username", required = true) String Username,
                       @RequestParam(name = "password", required = true) String Password,
                       @RequestParam(name = "Ruolo", required = true) String Role) {
        Server s = new Server();
         s.insertUser(Username,Password,Role);
         return "admin";
    }

    }