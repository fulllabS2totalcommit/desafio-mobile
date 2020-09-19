//
//  ImageDownload.swift
//  MobFiq
//
//  Created by Vinicius on 28/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import Foundation
import UIKit

let imageCache = NSCache<AnyObject, AnyObject>()

extension UIImageView {
    
    
    
    func loadImageUsingCacheWithString(urlString: String){
        
        self.image = nil
        //checando o cache para a imagem
        
        if let cacheImage = imageCache.object(forKey: urlString as AnyObject){
            self.image = cacheImage as! UIImage
            return
        }
        
        let url = URL(string: urlString)
        URLSession.shared.dataTask(with: url!) {data,response,error in
            
            if (error != nil){
                print(error)
                return
            }
            DispatchQueue.main.async {
                
                if let downloadImage = UIImage(data: data!){
                    
                    imageCache.setObject(downloadImage, forKey: urlString as AnyObject)
                    self.image = downloadImage
                    
                    
                    
                }
                
            }
            
            }.resume()
    }
    
    
    
    
    
    func downloadedFrom(url: URL, contentMode mode: UIViewContentMode = .scaleAspectFit) {
            contentMode = mode
            URLSession.shared.dataTask(with: url) { (data, response, error) in
                guard let httpURLResponse = response as? HTTPURLResponse, httpURLResponse.statusCode == 200,
                    let mimeType = response?.mimeType, mimeType.hasPrefix("image"),
                    let data = data, error == nil,
                    let image = UIImage(data: data)
                    else { return }
                DispatchQueue.main.async() { () -> Void in
                    self.image = image
                }
                }.resume()
        }
        func downloadedFrom(link: String, contentMode mode: UIViewContentMode = .scaleAspectFit) {
            guard let url = URL(string: link) else { return }
            downloadedFrom(url: url, contentMode: mode)
        }
    
}
