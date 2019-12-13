
import React, {useState,useEffect} from 'react';
import {View,Text,Button} from 'react-native';
import api from '../services/api'

export default function Produtos(){
    const[produto, setProdutos]=useState('');
        
    useEffect(()=>{
            async function loadProdutos(){
            const response = await api.post('Search/Criteria', {
                "Query" : "" ,
                "Offset": 0 ,
                "Size": 10 ,
               
             }
            );
            const dados =response.data
            console.log(dados);
        }
        loadProdutos()
    })

    return(
       <View>
           <Text>teste</Text>
       </View>
      );
}