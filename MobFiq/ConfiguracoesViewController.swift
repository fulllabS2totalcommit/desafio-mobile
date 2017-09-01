//
//  ConfiguracoesViewController.swift
//  MobFiq
//
//  Created by Joel Sene on 31/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import UIKit

class ConfiguracoesViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
   @IBOutlet weak var tableView : UITableView!
    var identifier = "cell"
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.navigationController?.navigationBar.tintColor = UIColor.red
        self.navigationController?.navigationBar.barTintColor = UIColor.black
         navigationItem.leftBarButtonItem = UIBarButtonItem(title: "Voltar", style: .plain, target: self, action: #selector(voltar))
        navigationItem.title = "Configurações"
        
        
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 20
        // Do any additional setup after loading the view.
        
         self.tableView.reloadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    
    
    func voltar(){
        
        self.dismiss(animated: true, completion: nil)
    }

    
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! ConfiguracoesTableViewCell
        
        cell.texto.text = "Desabilitar notificações"
        
        if cell.statusNotificacoes.isOn == true {
            
            cell.statusNotificacoes.isOn = false
            
        }
        else {
            
            cell.statusNotificacoes.isOn  = true
        }
        
       
        
        return cell
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
