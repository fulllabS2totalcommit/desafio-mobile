//
//  CategoriaViewController.swift
//  MobFiq
//
//  Created by Joel Sene on 31/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import UIKit
import SystemConfiguration

class CategoriaTableViewController: UITableViewController {
    
    var listsCategorias = [String]()
    var listaSubCategorias = [String]()
    var listaSubCategorias2 = [String]()
    var listaSubCategorias3 = [String]()
    var listaSubCategorias4 = [String]()
    var listaSubCategorias5 = [String]()
    var identifier = "cell"
    var alerta = AlertaProtocolo()
    
    let activityindicator : UIActivityIndicatorView = UIActivityIndicatorView()
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.navigationController?.navigationBar.tintColor = UIColor.red
        self.navigationController?.navigationBar.barTintColor = UIColor.black
        
        navigationItem.title = "Categorias"
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 60.0
        activityindicator.center = self.view.center
        activityindicator.activityIndicatorViewStyle = .gray
        activityindicator.color = UIColor.red
        
        self.view.addSubview(activityindicator)
        buscarCategorias()
        
        // Do any additional setup after loading the view.
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return listsCategorias.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: identifier, for: indexPath) as! CategoriaTableViewCell
        let item = listsCategorias[indexPath.row]
        
        cell.nomeCategoria.text = item
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        tableView.deselectRow(at: indexPath, animated: false)
        
       let item = listsCategorias[indexPath.row]
        
        switch item {
        case "Beleza":
            irParaSubCategorias(subCategorias: listaSubCategorias)
        break
        case "Boa Forma & SPA":
            irParaSubCategorias(subCategorias: listaSubCategorias2)
        break
        case "Cozinha":
            irParaSubCategorias(subCategorias: listaSubCategorias3)
        break
        case "Casa":
            irParaSubCategorias(subCategorias: listaSubCategorias4)
        break
        case "Eletrônicos":
            irParaSubCategorias(subCategorias: listaSubCategorias5)
        break
        default:
            print( "sem categoria")
        }
        
        
        
    }
    
    func irParaSubCategorias(subCategorias : [String]){
        
        
        let subCategoriaVC = UIStoryboard(name: "SubCategoria", bundle: nil).instantiateViewController(withIdentifier: "subcategoriaController") as! SubCategoriaTableViewController
        
        subCategoriaVC.listaSubCategorias = subCategorias
        
        self.navigationController?.pushViewController(subCategoriaVC, animated: true)
    }
    
    func buscarCategorias() {
        
        
        if verificaSeTemInternet() == true {
            
            activityindicator.startAnimating()
            
            
            //let url = URL(string: "https://desafio.mobfiq.com.br/StorePreference/CategoryTree")!
            let urlComponent = URLComponents(string: "https://desafio.mobfiq.com.br/StorePreference/CategoryTree")
            let session = URLSession.shared
            
            //var request = URLRequest(url : url)
            // request.httpMethod = "GET"
            
            /*  do {
             request.httpBody = try JSONSerialization.data(withJSONObject: [], options: .prettyPrinted)
             } catch let error {
             print(error.localizedDescription)
             }*/
            
            //request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            // request.addValue("application/json", forHTTPHeaderField: "Accept")
            guard let url = urlComponent?.url else {return}
            
            
            
            
            //create dataTask using the session object to send data to the server
            
            let task = session.dataTask(with: url) { (data, response, error) in
                
                
                guard error == nil else {
                    return
                }
                guard let data = data else {
                    return
                }
                
                let response = response as? HTTPURLResponse
                if response?.statusCode == 200 {
                    
                    do {
                        if let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as? [String: Any] {
                            
                            print(json)
                            
                            
                            
                            guard let categorias = json["Categories"] as? [[String: AnyObject]] else {return}
                            
                            for categoria in categorias {
                                
                                let nome = categoria["Name"] as! String
                                
                                if nome == "Beleza" {
                                    
                                    let subCategoria = "SubCategories"
                                    
                                    self.buscaSubCategoria(subCategorias: categoria, text: subCategoria, nome: nome)
                                }
                                
                                if nome == "Boa Forma & SPA" {
                                    
                                    let subCategoria = "SubCategories"
                                    
                                    self.buscaSubCategoria(subCategorias: categoria, text: subCategoria, nome: nome)
                                }
                                
                                if nome == "Cozinha" {
                                    
                                    let subCategoria = "SubCategories"
                                    
                                    self.buscaSubCategoria(subCategorias: categoria, text: subCategoria, nome: nome)
                                }
                                
                                if nome == "Casa" {
                                    
                                    let subCategoria = "SubCategories"
                                    
                                    self.buscaSubCategoria(subCategorias: categoria, text: subCategoria, nome: nome)
                                }
                                
                                if nome == "Eletrônicos" {
                                    
                                    let subCategoria = "SubCategories"
                                    
                                    self.buscaSubCategoria(subCategorias: categoria, text: subCategoria, nome: nome)
                                }
                                
                                
                                
                                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                                    
                                    self.activityindicator.stopAnimating()
                                    
                                    self.listsCategorias.append(nome)
                                    self.tableView.reloadData()
                                })
                            }
                            
                            
                            
                        }
                        
                    }
                    catch let error {
                        print(error.localizedDescription)
                        
                        self.alertasCategoria(tipoAlerta: "erroServer")
                    }
                    
                }
            }
            task.resume()
        }
        else {
            
            self.alertasCategoria(tipoAlerta: "semNet")
            
        }
        
        
    }
    
    func voltar(){
        
        self.dismiss(animated: true, completion: nil)
    }
    
    
    func buscaSubCategoria(subCategorias: [String: AnyObject], text : String, nome : String) {
        
        if nome == "Beleza" {
            
            guard let sub = subCategorias[text] as? [[String: AnyObject]] else {
                return
            }
            
            for subis in sub {
                
                
                let nome = subis["Name"] as! String
                
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                    
                    self.listaSubCategorias.append(nome)
                })
            }
        }
        
        if nome == "Boa Forma & SPA" {
            
            guard let sub = subCategorias[text] as? [[String: AnyObject]] else {
                return
            }
            
            for subis in sub {
                
                
                let nome = subis["Name"] as! String
                
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                    
                    self.listaSubCategorias2.append(nome)
                })
            }
        }
        
        if nome == "Cozinha" {
            
            guard let sub = subCategorias[text] as? [[String: AnyObject]] else {
                return
            }
            
            for subis in sub {
                
                
                let nome = subis["Name"] as! String
                
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                    
                    self.listaSubCategorias3.append(nome)
                })
            }
        }
        
        if nome == "Casa" {
            
            guard let sub = subCategorias[text] as? [[String: AnyObject]] else {
                return
            }
            
            for subis in sub {
                
                
                let nome = subis["Name"] as! String
                
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                    
                    self.listaSubCategorias4.append(nome)
                })
            }
        }
        
        if nome == "Eletrônicos" {
            
            guard let sub = subCategorias[text] as? [[String: AnyObject]] else {
                return
            }
            
            for subis in sub {
                
                
                let nome = subis["Name"] as! String
                
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                    
                    self.listaSubCategorias5.append(nome)
                })
            }
        }
        
        
        
        
        
    }
    
    
    func alertasCategoria(tipoAlerta: String){
        
        if tipoAlerta == "semNet" {
            
            let okSemDados = UIAlertAction(title: "Tentar novamente", style: .default, handler: { (UIAlertAction) in
                
                
                self.buscarCategorias()
                
            })
            
            let okErroCadastro = UIAlertAction(title: "Cancelar", style: .default, handler: { (UIAlertAction) in
                
                self.voltar()
                
                
            })
            
            let alertVC = UIAlertController(title: "Sem Internet ", message: "Você esta sem conexão no momento", preferredStyle: .alert)
            alertVC.addAction(okSemDados)
            alertVC.addAction(okErroCadastro)
            self.present(alertVC, animated: true, completion: nil)
        }
        else if tipoAlerta == "erroServer" {
            
            let okSemDados = UIAlertAction(title: "Tentar novamente", style: .default, handler: { (UIAlertAction) in
                
                
                self.buscarCategorias()
                
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
