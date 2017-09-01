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
    var usuario = [Usuario]()
    var indicator : UIActivityIndicatorView = UIActivityIndicatorView()
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        //buscarPerfil()
    }
    
    
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
        
       // buscarPerfil()
        // Do any additional setup after loading the view.
    }
    
    
    func voltar(){
        
        self.dismiss(animated: true, completion: nil)
    }
    
    func buscarPerfil(){
        
        
        
        
        
        nomeUsuario.isHidden = true
        emailUsuario.isHidden = true
        telefoneUsuario.isHidden = true
        sexoUsuario.isHidden = true
        senhaUsuario.isHidden = true
        
        let teste = Auth.auth(app: )
        
        if Auth.auth().currentUser?.uid == nil {
            print("testes")
            
        }
        else {
            indicator.startAnimating()
            
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
               //  self.indicator.stopAnimating()
                
                self.nomeUsuario.isHidden = false
                self.emailUsuario.isHidden = false
                self.telefoneUsuario.isHidden = false
                self.sexoUsuario.isHidden = false
                self.senhaUsuario.isHidden = false
                
                let ref = Auth.auth().createUser
                let uid = Auth.auth().currentUser?.uid
                
                Database().reference().child("usuariosMobfiq").child(uid!).observeSingleEvent(of: .value, with: { (snapshot) in
                    
                    if let dictionary = snapshot.value as? [String: Any] {
                        
                        let dadosUsuario = Usuario()
                        dadosUsuario.setValuesForKeys(dictionary)
                        self.usuario.append(dadosUsuario)
                        
                        
                        
                        
                        
                        if let imagemUrl = dadosUsuario.imagemPerfil {
                            
                            self.imagemUsuario.loadImageUsingCacheWithString(urlString: imagemUrl)
                        }
                        
                        
                        
                        self.nomeUsuario.text = dadosUsuario.nome
                        self.emailUsuario.text = dadosUsuario.email
                        self.telefoneUsuario.text = dadosUsuario.telefone
                        self.sexoUsuario.text = dadosUsuario.sexo
                        self.senhaUsuario.text = dadosUsuario.senha
                        
                        
                        
                        
                    }
                })
                
            })
            
            
            
        }
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
