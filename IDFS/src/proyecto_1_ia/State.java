/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_1_ia;

import java.util.ArrayList;

/**
 *
 * @author deyban.perez
 */
public class State 
{
    private Position luke;
    private ArrayList<Position> stormtroopers;
    private ArrayList<Position> walls;
    private String movements;
    private int steps;
    
    State(Position luke, ArrayList<Position> stormtroopers,
            ArrayList<Position> walls, String movements, int steps)
    {
        this.luke = luke;
        this.stormtroopers = stormtroopers;
        this.walls = walls;
        this.movements = movements;
        this.steps = steps;
    }
     
    public Position getLuke()
    {
        return this.luke;
    }
    
    public void setLuke(Position luke)
    {
        this.luke=luke;
    }
    
    public ArrayList<Position> getStormtroopers()
    {
        return this.stormtroopers;
    }
    
    public void setStormtroopers(ArrayList<Position> stormtroopers)
    {
        this.stormtroopers=stormtroopers;
    }
    
    public ArrayList<Position> getWalls()
    {
        return this.walls;
    }
    
    public void setWalls(ArrayList<Position> walls)
    {
        this.walls=walls;
    }
    
    public String getMovements()
    {
        return this.movements;
    }
    
    public void setMovements(String movements)
    {
        this.movements=movements;
    }
    
    public int getSteps()
    {
        return this.steps;
    }
    
    public void setSteps(int steps)
    {
        this.steps=steps;
    }
}
