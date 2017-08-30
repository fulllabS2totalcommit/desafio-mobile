//
//  VitrineViewController.swift
//  MobFiq
//
//  Created by Vinicius on 27/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import UIKit

enum selecionandoTextoParaPesquisa: Int{
    
    case nome = 0
    
}

class VitrineViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, UISearchBarDelegate {
    
    @IBOutlet weak var resultadoLabel : UILabel!
     @IBOutlet weak var resultado : UILabel!
     @IBOutlet weak var textoPesquisadoLabel : UILabel!
     @IBOutlet weak var textoPesquisado : UILabel!
    var listaProduto = [Produto]()
    var identifierCell = "collectionCell"
    var identifierReuseCell = "collectionResuseCell"
    @IBOutlet weak  var collectionView : UICollectionView!
    @IBOutlet weak var searchBar : UISearchBar!
    var listaProdutosFiltrados = [Produto]()
    var listaString = [String]()
    var listaFiltroString = [String]()
    var textFilter = 0
    var valorScroll = 10
   
    //var produtos = Produto(imagem: "jhjgh", nome: "camiseta", precoTabela: "299,99", precoFinal: "290,90", parcelamento: "10 x 29,90", desconto: "10%")

    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        self.view.endEditing(true)
    }

    
    //Presses return key
    @IBAction func textFieldShouldReturn(_ textField: UITextField)  {
        textField.resignFirstResponder()
    
    }


    func numberOfSections(in collectionView: UICollectionView) -> Int {
        
        return 1
    }
  
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
    
       /* var valor = 0
        if valorScroll < listaProduto.count {
            
            valor =  listaProduto.count
        }
        else {
            valor =  valorScroll
        }*/
        return listaProduto.count
    }
    
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: identifierCell, for: indexPath) as! VitrineCollectionViewCell
        
      //  let itensParaFiltro = listaFiltroString[indexPath.row]
        let itensProduto = listaProduto[indexPath.row]
        
        cell.nome.text = itensProduto.nome
        cell.precoTabela.text = "R$" + String(itensProduto.precoTabela)
        cell.imagem.loadImageUsingCacheWithString(urlString: itensProduto.imagem)
       // cell.parcelamento.text = String(itensProduto.parcelamento) + "x  R$"
        cell.valorDividido.text = String(itensProduto.parcelamento) + "x  R$" + String(itensProduto.valorParcelado)
        cell.precoTotal.text = "R$" + String(itensProduto.precoFinal)
        cell.desconto.text = String(calculoDesconto(valor1: itensProduto.precoTabela, valor2: itensProduto.precoFinal)) + "%"
        cell.viewFundo.layer.cornerRadius = 8
        cell.viewFundo.backgroundColor = UIColor.lightGray
        
        
        return cell
        
        
    }
    
    
    func calculoDesconto(valor1 : Double, valor2 : Double) -> Int{
        
        let porcentagemTotal = 100.0
        let valor = valor1 / valor2
        
        print(valor1)
        print(valor2)
        
        print(valor)
        
        
        let valorFormatado = String(format: "%.2f", Double(valor))
        print(valorFormatado)
        
        let valorPorcentagem = Double(valorFormatado)! * porcentagemTotal
        
        let desconto = porcentagemTotal - valorPorcentagem
        
        return Int(desconto)
        
    }
    
    
    func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        
        let lastSectionIndex = collectionView.numberOfSections - 1
        let lastRowIndex = collectionView.numberOfItems(inSection: lastSectionIndex) - 1
        if indexPath.section ==  lastSectionIndex && indexPath.row == lastRowIndex {
            // print("this is the last cell")
          //  var spinner = UIActivityIndicatorView(activityIndicatorStyle: .gray)
           // spinner.startAnimating()
            
           /* let refreshControl = UIRefreshControl()
            refreshControl.tintColor = .blue
            refreshControl.addTarget(self, action: #selector(teste), for: .valueChanged)
            collectionView.addSubview(refreshControl)*/
            collectionView.alwaysBounceVertical = true
            
          //  refreshControl.beginRefreshing()
            
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                
              //  refreshControl.endRefreshing()
                
               // listaProduto.append()
                
                
            })
            
           /* spinner.frame = CGRect(x: CGFloat(0), y: CGFloat(0), width: collectionView.bounds.width, height: CGFloat(44))
             self.collectionView.addSubview(spinner)
             self.collectionView?.refreshControl.isHidden = false
            self.collectionView.vi*/
            
        }
        
    }
    
    func teste(){
        print("tstete")
        
    }
    
   /* func scrollViewDidScroll(_ scrollView: UIScrollView) {
        

        
        let offsetY = scrollView.contentOffset.y
        let contentHeight = scrollView.contentSize.height
        if offsetY > contentHeight - scrollView.frame.size.height {
            
            let loading = UIActivityIndicatorView(frame: CGRect(x: 0, y: 0, width: collectionView.frame.width, height: 30))
            loading.activityIndicatorViewStyle = .gray
            
            collectionView.addSubview(loading)
            
            loading.startAnimating()
            
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now(), execute: { 
                
                loading.stopAnimating()
                self.collectionView.reloadData()
                
            })
            
        }
        
    }
    */
   
    
    func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
       
        
        
        switch kind {
        
        case UICollectionElementKindSectionFooter:
            
            let cell = collectionView.dequeueReusableSupplementaryView(ofKind: kind,
                withReuseIdentifier: identifierReuseCell, for: indexPath) as! VitrineCollectionReusableView
            
            
                cell.scroll?.activityIndicatorViewStyle = .gray
                
                cell.scroll?.startAnimating()
            
            
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                    
                    cell.scroll?.stopAnimating()
                    self.collectionView.reloadData()
                    
                })
            
            return cell
        default:
            //4
            assert(false, "Unexpected element kind")
        }
        
        

    }
    
   /* func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        let lastSectionIndex = tableView.numberOfSections - 1
        let lastRowIndex = tableView.numberOfRows(inSection: lastSectionIndex) - 1
        if indexPath.section ==  lastSectionIndex && indexPath.row == lastRowIndex {
            // print("this is the last cell")
            var spinner = UIActivityIndicatorView(activityIndicatorStyle: .gray)
            spinner.startAnimating()
            
            
            
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: {
                
                spinner.stopAnimating()
                
                
            })
            
            
            
            spinner.frame = CGRect(x: CGFloat(0), y: CGFloat(0), width: tableView.bounds.width, height: CGFloat(44))
           // self.collectionView = spinner
           // self.collectionView?.tableFooterView?.isHidden = false
        }
    }*/
    
    
    
    
    
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        
        
       /* if searchText.isEmpty {
            
            listaProduto = listaProdutosFiltrados
        }
        else {
            
            filterCollection(ind: searchBar.selectedScopeButtonIndex, text: searchText)
        }*/
        
        
        listaProduto = searchText.isEmpty ? listaProdutosFiltrados : listaProdutosFiltrados.filter { (item: Produto) -> Bool in
            // If dataItem matches the searchText, return true to include it
            
            if listaProduto.count == 1 {
                
                resultadoLabel.text = "Resultado"
            }
            else if listaProduto.count <= 0 {
                resultadoLabel.isHidden = true
                textoPesquisadoLabel.isHidden = true
                resultado.isHidden = true
                textoPesquisado.isHidden = true
                
            }
            
            else {
                resultadoLabel.text = "Resultado"
            }
            resultado.text = String(listaProduto.count)
            textoPesquisado.text = searchText
            
            return item.nome.lowercased().contains(searchText.lowercased())
        
        
        }
        
        self.collectionView.reloadData()
        

    }
    
    /*func filterCollection(ind: Int, text: String){
        switch ind {
        case selecionandoTextoParaPesquisa.nome.rawValue:
            listaProduto = listaProduto.filter({ (mod) -> Bool in
                return mod.nome.lowercased().contains(text.lowercased())
            })
            self.collectionView.reloadData()
            
        case selecionandoTextoParaPesquisa.precoTabela.rawValue:
            listaProduto = listaProduto.filter({ (mod) -> Bool in
                return String(mod.precoTabela).lowercased().contains(text.lowercased())
            })
            self.collectionView.reloadData()
            
        case selecionandoTextoParaPesquisa.preco.rawValue:
            listaProduto = listaProduto.filter({ (mod) -> Bool in
                return String(mod.precoFinal).lowercased().contains(text.lowercased())
            })
            self.collectionView.reloadData()
            
        case selecionandoTextoParaPesquisa.desconto.rawValue:
            listaProduto = listaProduto.filter({ (mod) -> Bool in
                return String(mod.desconto).lowercased().contains(text.lowercased())
            })
            self.collectionView.reloadData()
        default:
            print(" Sem dados")
        }
        
    }
    */
    
    func buscaProdutos(size : Int){
        
        
        
      
        let parameters =
            
            [
                "Query" : "",
                "Offset": 0,
                "Size": size
            ] as [String : Any]
        
        
        let url = URL(string: "https://desafio.mobfiq.com.br/Search/Criteria/")!
        
        let session = URLSession.shared
        
        var request = URLRequest(url : url)
        request.httpMethod = "POST"
        
        do {
            request.httpBody = try JSONSerialization.data(withJSONObject: parameters, options: .prettyPrinted)
        } catch let error {
            print(error.localizedDescription)
        }
        
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
       // request.addValue("application/json", forHTTPHeaderField: "Accept")

        
        //create dataTask using the session object to send data to the server
        let task = session.dataTask(with: request as URLRequest, completionHandler: { data, response, error in
            guard error == nil else {
                return
            }
            guard let data = data else {
                return
            }
            
            do {
                //criar o json data
                if let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as? [String: Any] {
                    // handle json...
                   // print(json )
                    print("teste Json!!!")
                    
                    
                            
                            guard let produtos = json["Products"] as? [[String: AnyObject]] else {
                                return }
                            
                            for produto in produtos {
                                
                                guard let skus = produto["Skus"] as? [[String: AnyObject]] else {
                                    return }
                                
                                
                                for valores in skus {
                                    
                                    var produtosModel = Produto()
                                    
                                    print(valores["Name"] as! String)
                                    produtosModel.nome = valores["Name"] as! String
                                    
                                    
                                    guard let sellers = valores["Sellers"] as? [[String: AnyObject]] else {return}
                                    
                                    for seller in sellers {
                                        
                                        if let selletTest = seller["ListPrice"] {
                                            produtosModel.precoFinal = selletTest as! Double
                                        }
                                        if let selleTestPrice = seller["Price"] {
                                             produtosModel.precoTabela = selleTestPrice as! Double
                                        }
                                       
                                        if let dadosMelhorValor = seller["BestInstallment"] as? [String: AnyObject] {
                                            
                                            
                                            let vezesParcelamento = dadosMelhorValor["Count"] as! Int
                                            
                                            let valoresParcela = dadosMelhorValor["Value"] as! Double
                                            
                                            produtosModel.parcelamento = vezesParcelamento
                                            produtosModel.valorParcelado = valoresParcela
                                            
                                        }
                                        
                                      
                                        
                                        
                                    }
                                    
                                    
                                    
                                    guard let images = valores["Images"] as? [[String: AnyObject]] else {return}
                                    
                                    for imagem in 0 ..< images.count {
                                        
                                         let imagemUrl = images[0]["ImageUrl"] as! String
                                        
                                        
                                            produtosModel.imagem = imagemUrl
                                        
                                            
                                        
                                    }
                                    
                                    
                                    
                                 /*   for precos in melhorPreco {
                                        
                                        let vezes = precos["Count"] as! Double
                                        let valorDividido = precos["Value"] as! Double
                                        let desconto = precos["Rate"] as! Double
                                        
                                        produtosModel.desconto = desconto
                                        produtosModel.parcelamento = vezes
                                        produtosModel.valorParcelado = valorDividido
                                    }*/
                                    
                                    DispatchQueue.main.asyncAfter(deadline: DispatchTime.now(), execute: { 
                                        
                                        self.listaProduto.append( produtosModel)

                                        
                                        self.listaProdutosFiltrados = self.listaProduto
                                        self.collectionView.reloadData()
                                        
                                    })
                                 
                                    
                                }
                            }
                    
                        
                        
                }
                
            } catch let error {
                print(error.localizedDescription)
                
               
                
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 0.5, execute: {
                    
                   
                    
                    DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.5, execute: {
                        
                        
                        
                    })
                    
                })
                
            }
            
            
        })
        task.resume()
        
        
    }
    
    
    func pesquisarProduto() {
        
        
        
        
            searchBar.isHidden = false
            resultadoLabel.isHidden = false
            textoPesquisadoLabel.isHidden = false
            resultado.isHidden = false
            textoPesquisado.isHidden = false
            navigationItem.rightBarButtonItem = UIBarButtonItem(image: UIImage(named:"imageClose"), style: .plain, target: self, action: #selector(fechaPesquisa))
            
     
        
    }
    
    func fechaPesquisa(){
        
        collectionView.frame = CGRect(x: 0, y: 60, width: UIScreen.main.bounds.size.width, height: UIScreen.main.bounds.size.height)
        
        searchBar.isHidden = true
        resultadoLabel.isHidden = true
        textoPesquisadoLabel.isHidden = true
        resultado.isHidden = true
        textoPesquisado.isHidden = true
        
        resultado.text = ""
        textoPesquisado.text = ""
        
        
         navigationItem.rightBarButtonItem = UIBarButtonItem(image: UIImage(named:"imageSearch"), style: .plain, target: self, action: #selector(pesquisarProduto))
    }
    
    /*func loadListOfCountries() {
        // Specify the path to the countries list file.
        let pathToFile = Bundle.main.path(forResource: "countries", ofType: "txt")
        
        if let path = pathToFile {
            // Load the file contents as a string.
            
            
            
            let stringProdutos = NSString(contentsOfFile: path, encoding: String.Encoding.utf8.rawValue)
            
            
            // Append the countries from the string to the dataArray array by breaking them using the line change character.
            listaProduto = stringProdutos.components(separatedBy: "\n")
            
            // Reload the tableview.
            collectionView.reloadData()
        }
    }*/
    
    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
       // self.searchBar.showsCancelButton = true
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        collectionView.dataSource = self
        searchBar.delegate = self
        
        collectionView.frame = CGRect(x: 0, y: 0, width: UIScreen.main.bounds.size.width, height: UIScreen.main.bounds.size.height)
        navigationItem.title = "Produtos"
        self.navigationController?.navigationBar.tintColor = UIColor.init(colorLiteralRed: 128.0/255.0, green: 0.0/255.0, blue: 0.0/255.0, alpha: 0.1)
        navigationController?.navigationBar.barTintColor = UIColor.black
        
        let seletorPesquisa : Selector = #selector(pesquisarProduto)
        navigationItem.rightBarButtonItem = UIBarButtonItem(image: UIImage(named: "imageSearch"), style: .plain, target: self, action: seletorPesquisa)
        
        buscaProdutos(size: 10)
        
        
        
        
        
        
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