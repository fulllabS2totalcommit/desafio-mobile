//
//  AlertaProtocolo.swift
//  MobFiq
//
//  Created by Vinicius on 29/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import UIKit


var login = LoginViewController()

protocol AlertaDelegate {
    
    func alertasDeErro(stringTexto: String, titulo : String, mensagem : String, textField: UITextField, controller: UIViewController, funcao : Void)
    
    
}

class AlertaProtocolo : UIViewController, AlertaDelegate {
    
    
    
    func alertasDeErro(stringTexto: String, titulo: String, mensagem: String, textField: UITextField, controller: UIViewController, funcao: Void) {}

    
    
    
    
    
        
    
       
    
  
    
   
    
    
  /*internal  func alertasDeErro(stringTexto: String, titulo: String, mensagem: String, textField: UITextField, controller: UIViewController) {
        
        
        let okSemDados = UIAlertAction(title: "OK", style: .default, handler: { (UIAlertAction) in
            
            textField.text = ""
            textField.becomeFirstResponder()
            
            
            
        })
        
        let okErroCadastro = UIAlertAction(title: "OK", style: .default, handler: { (UIAlertAction) in
            
            
            
            
        })
        
        let alertVC = UIAlertController(title: titulo, message: mensagem, preferredStyle: .alert)
        
        
        if stringTexto == "cadastro" {
            
            alertVC.addAction(okSemDados)
            controller.present(controller, animated: true, completion: nil)
        }
            
        else if stringTexto == "semNet"{
            
            alertVC.addAction(okErroCadastro)
            controller.present(controller, animated: true, completion: nil)
            
        }
        
        
    }*/

        
        
        
    
}


    
    var delegate : AlertaDelegate? = nil

    
  
