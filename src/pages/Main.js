
import React, { Component } from 'react';
import { StyleSheet, View, Text, Button } from 'react-native';
import Header from '../components/header'
import Produto from '../components/produtos'
 
export default function Main(){

        return (
          <View>
            <Header />
            <Produto />
          </View>
        );
    }
