//
//  LoginViewController.swift
//  MobFiq
//
//  Created by Vinicius on 29/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import UIKit
import Firebase
import SystemConfiguration

class LoginViewController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var emailTextfield : UITextField!
    @IBOutlet weak var senhaTextfield : UITextField!
    @IBOutlet weak var botaoLogar : UIButton!
    @IBOutlet weak var botaoCadastrar : UIButton!
    var alerta = AlertaProtocolo()
    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        self.view.endEditing(true)
    }
    
    
    //Presses return key
    @IBAction func textFieldShouldReturn(_ textField: UITextField)  {
        textField.resignFirstResponder()
        
    }
    
    func logar() {
        
        if emailTextfield.text == "" || senhaTextfield.text == "" {
            
            
            self.alertasLogin(tipoAlerta: "faltaDados")
            
           
            emailTextfield.text = ""
            self.emailTextfield.becomeFirstResponder()
            senhaTextfield.text = ""
        }
            
        else {
            
            
            let loading = UIActivityIndicatorView(activityIndicatorStyle: .gray)
            loading.center = self.view.center
            loading.hidesWhenStopped = false
            loading.color = UIColor.red
            
            
            self.view.addSubview(loading)
            
            loading.startAnimating()
            
            if verificaSeTemInternet() == true {
                
                guard let email = emailTextfield.text, let senha = senhaTextfield.text
                    
                    else{
                        print("Form is not valid")
                        return
                }
                
                
                print("teste")
                
                
                
                
                
                
                Auth.auth().signIn(withEmail: email, password: senha, completion: { (user, error) in
                    
                    
                    if error != nil{
                        print(error)
                        
                        self.alertasLogin(tipoAlerta: "usuarioExistente")
                        return
                    }
                    
                    
                    
                    
                    
                    
                    DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                        
                        loading.stopAnimating()
                        
                        
                        self.performSegue(withIdentifier: "menuController", sender: nil)
                        
                    })
                    
                    
                    
                    
                    
                    
                    
                    
                })
            }
            
        
        else {
            
            self.alertasLogin(tipoAlerta: "semNet")
        }
        
    

        
        }
    }

        
        func verificaSeTemInternet() -> Bool
        {
            var zeroAddress = sockaddr_in()
            zeroAddress.sin_len = UInt8(MemoryLayout.size(ofValue: zeroAddress))
            zeroAddress.sin_family = sa_family_t(AF_INET)
            
            let defaultRouteReachability = withUnsafePointer(to: &zeroAddress) {
                $0.withMemoryRebound(to: sockaddr.self, capacity: 1) {zeroSockAddress in
                    SCNetworkReachabilityCreateWithAddress(nil, zeroSockAddress)
                }
            }
            
            var flags = SCNetworkReachabilityFlags()
            if !SCNetworkReachabilityGetFlags(defaultRouteReachability!, &flags) {
                return false
            }
            let isReachable = (flags.rawValue & UInt32(kSCNetworkFlagsReachable)) != 0
            let needsConnection = (flags.rawValue & UInt32(kSCNetworkFlagsConnectionRequired)) != 0
            return (isReachable && !needsConnection)
        }

    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "menuController" {
            
            let nav = segue.destination as! UINavigationController
            nav.topViewController as! MenuViewController
        }
    }

    
    func cadastrar() {
        
        let cadastroVC = UIStoryboard(name: "Cadastro", bundle: nil).instantiateViewController(withIdentifier: "cadastroController") as! CadastroViewController
        
        self.navigationController?.pushViewController(cadastroVC, animated: true)
    }

    
  
        
    func alertasLogin(tipoAlerta: String){
        
        
        
        
        if tipoAlerta == "faltaDados" {
            
            let okSemDados = UIAlertAction(title: "OK", style: .default, handler: { (UIAlertAction) in
                
                
                
                
            })
            
            let okErroCadastro = UIAlertAction(title: "Cancelar", style: .default, handler: { (UIAlertAction) in
                
                
                
                
            })
            
            let alertVC = UIAlertController(title: "Dados imcompletos ", message: "Refaça o login", preferredStyle: .alert)
            alertVC.addAction(okSemDados)
            alertVC.addAction(okErroCadastro)
            self.present(alertVC, animated: true, completion: nil)
            
        }
            
            
            
            
        else if tipoAlerta == "emailErrado" {
            
            let okSemDados = UIAlertAction(title: "OK", style: .default, handler: { (UIAlertAction) in
                
                
                
                
            })
            
            let okErroCadastro = UIAlertAction(title: "Cancelar", style: .default, handler: { (UIAlertAction) in
                
                
                
                
            })
            
            let alertVC = UIAlertController(title: "Email ou senha incorretos ", message: "Refaça o login", preferredStyle: .alert)
            alertVC.addAction(okSemDados)
            alertVC.addAction(okErroCadastro)
            self.present(alertVC, animated: true, completion: nil)
        }
        
        
        
    }



    override func viewDidLoad() {
        super.viewDidLoad()
        
        emailTextfield.becomeFirstResponder()
        
        navigationItem.title = "Login"
        botaoLogar.addTarget(self, action: #selector(logar), for: .touchUpInside)
        botaoLogar.layer.cornerRadius = 10
        botaoCadastrar.addTarget(self, action: #selector(cadastrar), for: .touchUpInside)
        botaoCadastrar.layer.cornerRadius = 10

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
