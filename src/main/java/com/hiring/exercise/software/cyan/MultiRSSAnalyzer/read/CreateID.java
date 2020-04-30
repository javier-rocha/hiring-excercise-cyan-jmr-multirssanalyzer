/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiring.exercise.software.cyan.MultiRSSAnalyzer.read;

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author jamar
 */
public class CreateID {
    
    public static String createID() {
        
        return String.valueOf(UUID.randomUUID());
    }
}
