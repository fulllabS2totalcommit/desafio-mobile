//
//  MenuViewController.swift
//  MobFiq
//
//  Created by Vinicius on 31/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import UIKit
import SystemConfiguration

class MenuViewController: UIViewController {
    
    
    @IBOutlet weak var botaoVitrine : UIButton!
    @IBOutlet weak var botaoCategorias : UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = "Menu"
        
        let seletorVitrine : Selector = #selector(mostrarVitrine)
        let seletorCategoria : Selector = #selector(mostrarCategoria)
        botaoVitrine.addTarget(self, action: seletorVitrine, for: .touchUpInside)
        botaoVitrine.layer.cornerRadius = 20
        botaoCategorias.layer.cornerRadius = 20
        botaoCategorias.addTarget(self, action: seletorCategoria, for: .touchUpInside)
        navigationItem.leftBarButtonItem = UIBarButtonItem(image: UIImage(named: "imageUser"), style: .plain, target: self, action: #selector(perfil))
        self.navigationController?.navigationBar.tintColor = UIColor.red
        self.navigationController?.navigationBar.barTintColor = UIColor.black
        // Do any additional setup after loading the view.
    }
    
    
    func mostrarVitrine() {
        
        let vitrineVC = UIStoryboard( name: "Main", bundle: nil).instantiateViewController(withIdentifier: "vitrineController") as! VitrineViewController
        navigationController?.pushViewController(vitrineVC , animated: true)
        
        
    }
    
    func mostrarCategoria() {
        
        let categoriaVC = UIStoryboard(name: "Categoria", bundle: nil).instantiateViewController(withIdentifier: "categoriaController") as! CategoriaTableViewController
        navigationController?.pushViewController(categoriaVC , animated: true)
        
    }
    
    func perfil(){
        
        
                
        self.performSegue(withIdentifier: "tabBar", sender: nil)
        
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "tabBar" {
            
            
          //  let nav = segue.destination as! UINavigationController
            
           // let destino = nav.topViewController as! UITabBarController
           // let ir = destino as! UITabBarController
            
            
            
            let nav = segue.destination as! UITabBarController
            
            let des = nav.viewControllers?[0] as! UINavigationController
            
            let ir = des.viewControllers[0] as! PerfilViewController
            
            
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
