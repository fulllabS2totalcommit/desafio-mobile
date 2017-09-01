//
//  CadastroViewController.swift
//  MobFiq
//
//  Created by Vinicius on 29/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import UIKit
import Firebase
import SystemConfiguration
import MediaPlayer
import MobileCoreServices

class CadastroViewController: UIViewController, UITextFieldDelegate, UIPickerViewDelegate, UIPickerViewDataSource, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    @IBOutlet weak var nomeTextfield : UITextField!
    @IBOutlet weak var emailTextfield : UITextField!
    @IBOutlet weak var telefoneTextfield : UITextField!
    @IBOutlet weak var sexoTextfield : UITextField!
    @IBOutlet weak var senhaTextfield : UITextField!
    @IBOutlet weak var botaoCamera : UIButton!
    @IBOutlet weak var botaoGaleria : UIButton!
    @IBOutlet weak var imagemUsuario : UIImageView!
    @IBOutlet weak var viewFundo : UIView!
    var pickerSexo = UIPickerView()
    var listaSexo : [String] = ["Masculino", "Feminino", "Prefiro não informar..."]
    @IBOutlet weak var botaoCadastro : UIButton!
    var teste = AlertaProtocolo()
    
    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        self.view.endEditing(true)
    }
    
    
    //Presses return key
    @IBAction func textFieldShouldReturn(_ textField: UITextField)  {
        textField.resignFirstResponder()
        
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return listaSexo.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        return listaSexo[row]
        
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
         sexoTextfield.text = listaSexo[row]
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        navigationItem.title = "Cadastro"
         //self.navigationController?.navigationBar.tintColor = UIColor.init(colorLiteralRed: 128.0/255.0, green: 0.0/255.0, blue: 0.0/255.0, alpha: 0.1)
        
        pickerSexo.dataSource = self
        pickerSexo.delegate = self
        
        //teste.delegate = self
        
        sexoTextfield.inputView = pickerSexo
        
        sexoTextfield.text = listaSexo[0]
        
        
        botaoGaleria?.addTarget(self, action: #selector(selecionarGaleria), for: .touchUpInside)
        botaoCamera?.addTarget(self, action: #selector(tirarFoto), for: .touchUpInside)
        
        botaoCadastro.addTarget(self, action: #selector(cadastrar), for: .touchUpInside)
        botaoCadastro.layer.cornerRadius = 10

        
        let toolBar = UIToolbar()
        toolBar.barStyle = UIBarStyle.default
        toolBar.isTranslucent = true
        toolBar.tintColor = UIColor.blue
        toolBar.sizeToFit()
        
        let doneButton = UIBarButtonItem(title: "Done", style: UIBarButtonItemStyle.plain, target: self, action: "donePicker")
        
        
        toolBar.setItems([ doneButton], animated: false)
        toolBar.isUserInteractionEnabled = true
        
        // modeloTextfield?.inputAccessoryView = toolBar
        sexoTextfield?.inputAccessoryView = toolBar
        
        // Do any additional setup after loading the view.
    }
    
    func donePicker() {
        
        sexoTextfield?.resignFirstResponder()
    }
    
    
    func getMediaUIImagePickerControllerSourceType(sourceType: UIImagePickerControllerSourceType) -> Void{
        
        let myMediaPicker: NSArray = UIImagePickerController.availableMediaTypes(for: sourceType)! as NSArray
        
        
        // caso seja diferente de zero significa que existe algum tipo de elemento mediapicker
        if myMediaPicker.count != 0 && UIImagePickerController.isSourceTypeAvailable(sourceType)
        {
            
            // passa o controle da aplicacao para esse tipo de elemento, sendo camera ou biblioteca
            let myPickerController : UIImagePickerController = UIImagePickerController()
            myPickerController.sourceType = sourceType
            
            // permitir que o usuario edite o arquivo que ele selecionar da biblioteca ou a foto que foi tirada
            myPickerController.allowsEditing = true
            myPickerController.mediaTypes = myMediaPicker as! [String]
            
            // aqui faz o retorno pra nossa propria classe que tera os metodos que irao trabalhar no arquivo retornado
            myPickerController.delegate = self
            // apresenta a nova tela
            self.present(myPickerController, animated: true, completion: nil)
        }
        else{
            
            let  myAlert : UIAlertView = UIAlertView()
            myAlert.title = " Não possui camera"
            myAlert.message = " Esta funcionalidade não pode ser ativada, pois seu dispositivo não possui camera"
            myAlert.addButton(withTitle: "OK")
            myAlert.show()
        }
        
        
    }
    
    func habilitarButtonCamera()
    {
        
        // verifica se o dispositivo possui camera
        if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.camera) == false
        {
            //propriedade hidden para setar como visivel ou invisivel, se for true esta invisivel
            // myButton.hidden = true
            let  myAlert : UIAlertView = UIAlertView()
            myAlert.title = " Não possui camera"
            myAlert.message = " Esta funcionalidade não pode ser ativada, pois seu dispositivo não possui camera"
            myAlert.addButton(withTitle: "OK")
            myAlert.show()
            
        }
        
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        
        var selectedImageFromPicker: UIImage?
        
        if let editedImage = info["UIImagePickerControllerEditedImage"] as? UIImage{
            
            selectedImageFromPicker = editedImage
            
        }
            
        else if let originalImage = info["UIImagePickerControllerOriginalImage"] as? UIImage{
            
            // print((originalImage as AnyObject).size)
            
            selectedImageFromPicker = originalImage
        }
        
        if let selectedImage = selectedImageFromPicker{
            
            imagemUsuario.image = selectedImageFromPicker
        }
        
        
        
        dismiss(animated: true, completion: nil)
    }
    
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        imagemUsuario.image = UIImage(named: "img_perfil_app_ios")
        
        picker.dismiss(animated: true, completion: nil)
    }
    
    func tirarFoto() {
        
        self.getMediaUIImagePickerControllerSourceType(sourceType: UIImagePickerControllerSourceType.camera)
    }
    
    
    func selecionarGaleria() {
        
        self.getMediaUIImagePickerControllerSourceType(sourceType: UIImagePickerControllerSourceType.photoLibrary)
    }

    
    func cadastroNoFirebase(uid: String, valores : [String: AnyObject]) {
        
        
        let ref =  Database.database().reference(fromURL: "https://ricci-boutique-xamarin.firebaseio.com/")
        
        let userReference = ref.child("usuariosMobfiq").child(uid)
        
        // values = ["nome": name, "sobrenome": email, "profileImageUrl": metadata.downloadUrl()]
        userReference.updateChildValues(valores, withCompletionBlock: { (err, ref) in
            
            if err != nil{
                print(err)
                return
            }
            
            self.dismiss(animated: true, completion: nil)
            
            
            
            // print("Save user successfully into Firebase db")
        })

        
    }
    
    
    func cadastrar() {
        
        let loading = UIActivityIndicatorView(activityIndicatorStyle: .gray)
        loading.center = self.view.center
        loading.hidesWhenStopped = false
        
        
        loading.color = UIColor.red
        self.view.addSubview(loading)
        
        loading.startAnimating()
        
        
        if verificaSeTemInternet() == true {
            
            if nomeTextfield.text == nil || emailTextfield.text == nil || telefoneTextfield.text == nil || senhaTextfield.text == nil {
                
                
                teste.alertasDeErro(stringTexto: "cadastro", titulo: "Dados Incompletos", mensagem: "Falta dados para cadastrar ...", textField: nomeTextfield, controller: self)
            }
                
            else {
                
                
                guard let nome = nomeTextfield.text, let email = emailTextfield.text, let senha = senhaTextfield.text, let telefone = telefoneTextfield.text, let sexo = sexoTextfield.text else {return}
                
                
                //autenticando user no firebase
                
                Auth.auth().createUser(withEmail: email, password: senha, completion: { (user, error) in
                    
                    if error != nil{
                        
                        var alert = UIAlertView()
                        alert.title = "Erro ao cadastrar!!!"
                        alert.message = "IHHHH voce ja se cadastrou, este  email ja esta em nosso banco, voce talvez tenha se cadastrado pelo facebook ou google e esta usando o mesmo email, tente logar por uma dessas contas ou utilize ou email hehe :)"
                        alert.addButton(withTitle: "OK")
                        alert.show()
                        print(error)
                        return
                    }
                    
                    
                    guard let uid = user?.uid else {
                        return
                    }
                    
                    let imageName = NSUUID().uuidString
                    
                    let storageRef = Storage.storage().reference().child("imagens_usuarios").child("\(imageName).png")
                    
                    if let uploadData = UIImagePNGRepresentation((self.imagemUsuario.image)!){
                        
                        let newData = UIImageJPEGRepresentation(UIImage(data: uploadData)!, 1)
                        
                        
                        storageRef.putData(newData!, metadata: nil, completion: { (metadata, error) in
                            if error != nil{
                                print(error)
                                return
                            }
                            
                            if let imagemPerfil = metadata?.downloadURL()?.absoluteString{
                                
                                
                                
                                
                                let valores = ["nome": nome, "email": email, "telefone": telefone, "sexo": sexo, "imagemPerfil": imagemPerfil, "senha": senha]
                    
                    
                                self.cadastroNoFirebase(uid: uid, valores: valores as [String : AnyObject])
                                
                                
                                
                                
                                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2.0, execute: { 
                                    
                                    loading.stopAnimating()
                                    self.navigationController?.popViewController(animated: true)
                                })
                                
                                
                            
                            }
                            
                    
                })
                            
                    }
                    
                })
                
                
            }
            
        }
        else {
            
            
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


