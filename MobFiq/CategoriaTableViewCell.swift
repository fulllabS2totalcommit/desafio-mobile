//
//  CategoriaTableViewCell.swift
//  MobFiq
//
//  Created by Joel Sene on 31/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import UIKit

class CategoriaTableViewCell: UITableViewCell {
    
    @IBOutlet weak var imagemCategoria : UIImageView!
    @IBOutlet weak var nomeCategoria : UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
