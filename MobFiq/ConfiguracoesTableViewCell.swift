//
//  ConfiguracoesTableViewCell.swift
//  MobFiq
//
//  Created by Joel Sene on 31/08/17.
//  Copyright © 2017 Vinicius. All rights reserved.
//

import UIKit

class ConfiguracoesTableViewCell: UITableViewCell {
    
    @IBOutlet weak var texto : UILabel!
    @IBOutlet weak var statusNotificacoes : UISwitch!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
