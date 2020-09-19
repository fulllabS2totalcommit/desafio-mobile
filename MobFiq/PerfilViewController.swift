//
//  PerfilViewController.swift
//  MobFiq
//
//  Created by Joel Sene on 31/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import UIKit
import SystemConfiguration
import Firebase
class PerfilViewController: UIViewController {
    
    @IBOutlet weak var imagemUsuario : UIImageView!
    @IBOutlet weak var nomeUsuario : UILabel!
    @IBOutlet weak var emailUsuario: UILabel!
    @IBOutlet weak var telefoneUsuario : UILabel!
    @IBOutlet weak var sexoUsuario : UILabel!
    @IBOutlet weak var senhaUsuario : UILabel!
    var alertas = AlertaProtocolo()
    var usuario = [Usuario]()
    var indicator : UIActivityIndicatorView = UIActivityIndicatorView()
    
   
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = "Perfil"
        
        self.navigationController?.navigationBar.tintColor = UIColor.red
        self.navigationController?.navigationBar.barTintColor = UIColor.black
        
        navigationItem.leftBarButtonItem = UIBarButtonItem(title: "Voltar", style: .plain, target: self, action: #selector(voltar))
        
        indicator.activityIndicatorViewStyle = .gray
        indicator.color = UIColor.red
        indicator.center = self.view.center
        self.view.addSubview(indicator)
        
        buscarPerfil()
        // Do any additional setup after loading the view.
    }
    
    
    func voltar(){
        
        self.dismiss(animated: true, completion: nil)
    }
    
    func buscarPerfil(){
        
        
        
        if verificaSeTemInternet() == true {
            
            nomeUsuario.isHidden = true
            emailUsuario.isHidden = true
            telefoneUsuario.isHidden = true
            sexoUsuario.isHidden = true
            senhaUsuario.isHidden = true
            
            
            
            if Auth.auth().currentUser?.uid == nil {
                print("testes")
                
            }
            else {
                indicator.startAnimating()
                
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                    //  self.indicator.stopAnimating()
                    
                    
                    
                    
                    let uid = Auth.auth().currentUser?.uid
                    
                    Database.database().reference().child("usuariosMobfiq").child(uid!).observeSingleEvent(of: .value, with: { (snapshot) in
                        
                        if let dictionary = snapshot.value as? [String: Any] {
                            
                            let dadosUsuario = Usuario()
                            dadosUsuario.setValuesForKeys(dictionary)
                            self.usuario.append(dadosUsuario)
                            
                            
                            self.nomeUsuario.isHidden = false
                            self.emailUsuario.isHidden = false
                            self.telefoneUsuario.isHidden = false
                            self.sexoUsuario.isHidden = false
                            self.senhaUsuario.isHidden = false
                            
                            
                            
                            
                            if let imagemUrl = dadosUsuario.imagemPerfil {
                                
                                self.imagemUsuario.loadImageUsingCacheWithString(urlString: imagemUrl)
                            }
                            
                            
                            
                            self.nomeUsuario.text = dadosUsuario.nome
                            self.emailUsuario.text = dadosUsuario.email
                            self.telefoneUsuario.text = dadosUsuario.telefone
                            self.sexoUsuario.text = dadosUsuario.sexo
                            self.senhaUsuario.text = dadosUsuario.senha
                            
                            
                            if self.imagemUsuario != nil {
                                
                                self.indicator.stopAnimating()
                            }
                            
                            
                            
                        }
                    })
                    
                })
                
                
                
            }

            
        }
        
        else {
            
            self.alertasPerfil(tipoAlerta: "semNet")
        }
        
    }
    
    func alertasPerfil(tipoAlerta: String){
        
        if tipoAlerta == "erroServer" {
            
            let okSemDados = UIAlertAction(title: "Tentar novamente", style: .default, handler: { (UIAlertAction) in
                
                
                self.buscarPerfil()
                
            })
            
            let okErroCadastro = UIAlertAction(title: "Cancelar", style: .default, handler: { (UIAlertAction) in
                
                self.voltar()
                
                
            })
            
            let alertVC = UIAlertController(title: "Erro de conexão com o servidor ", message: "Tente novamente mais tarde", preferredStyle: .alert)
            alertVC.addAction(okSemDados)
            alertVC.addAction(okErroCadastro)
            self.present(alertVC, animated: true, completion: nil)
        }
    }

    
    func verificaSeTemInternet() -> Bool {
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
