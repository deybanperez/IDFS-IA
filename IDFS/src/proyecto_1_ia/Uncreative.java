/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_1_ia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Cesar
 */
public class Uncreative 
{
    private static int n, m, limit;
    private State root;
    
    
    Uncreative()
    {
        this.root = init(); //Se lee el archivo y se inciializan las variables de la clase
        IDFS(); //Función que retorna el estado solución 
    }
    
    public void IDFS()
    {
        boolean solution = false; //Funciona para saber que mensajes imprimir
        
        for(int i=0; i<limit; i++) //El límite es el tiempo completo, ya que el tiempo 0 es tomado en cuenta y eso hace que se genere un estado más
        {
            State state = DLS(i); //Se busca el estado solución del problema
            
            if(state!=null) //Si encuentra solución la imprime y deja el ciclo de manera inmediata
            {
                solution = true;
                System.out.println("The Force is Strong in Luke!\n"+state.getMovements());
                break;
            }
        }
        
        if (!solution) 
            System.out.println("A great loss for the rebels!");
    }
    
    State DLS(int deep) //funcion que retorna el estado solucio´n, debemos pasarle la profundidad en cada paso
    {
        Stack<State> stack = new Stack <State>(); //Pila de estados posibles
        State temp; //Estado temporal que toma los valores de los estados de la pila
        ArrayList<State> newStates = new ArrayList<State>(); //Lista que almacena los posibles estados solución de la función checkSteps()
        stack.push(root); //Se apila la raiz
        root.setSteps(0); //Se inicializa el número de pasos en 0
        
        while(!stack.isEmpty()) //Mientras haya posibles soluciones
        {
            temp = stack.pop(); //Se desapila un posible estado solución
            
            if(temp.getStormtroopers().size() == 0) //Si el estado desapilado es una solución, se retorna dicho estado
                return temp;
            
            if(temp.getSteps() < deep) //Si el estado puede seguir creciendo en profundidad 
            {
                //Debo buscar los posible vecinos y apilarlos
                newStates = checkSteps(temp);
                
                if(newStates.size() > 0) //Si tiene posibles vecionos
                {
                    for(int i=0; i < newStates.size();i++) //Se recorre la lista y se apilan dichos estados
                        stack.push(newStates.get(i));   
                }
            }
        }
        
        return null; //Si no se encuentra solución, se retorna null
    }
    
    ArrayList<State> checkSteps(State actual) //Función que revisa todos los posibles nodos adyacentes dado un estado actual
    {
        ArrayList<Integer> forceReturn = new ArrayList<Integer>(); //Arreglo que retorna todas las posiciones de los Storm Stroopers a eliminar si se aplica la fuerza
        ArrayList<State> newStates = new ArrayList<State>(); //Lista que almacena todos los posibles estados válidos
        int pos_x, pos_y,shootValue; //shootValue tiene el valor de retorno del Storm Strooper a eliminar si se dispara en alguna de las direcciones 
        
        pos_x = actual.getLuke().getPos_x(); //Por simplicidad se asignan a variables temporales las posiciones de Luke
        pos_y = actual.getLuke().getPos_y();
        
        //Verificar los movimientos
        
        //Mover Sur
        if( (pos_x+1 < n) && (findStorm(actual,pos_x+1,pos_y)) && (findWall(actual,pos_x+1,pos_y)) && (dontBack(actual, 'S')) ) //Se verifica si el movimiento es a un espacio en blanco y dentro del tablero
        {
            //Agregar movimiento y actualizar la posicion de Luke
            //Acá hay que tener cuidado con la clonación de objetos, por eso se usó el método clone y se pasaron constructores como parámetros
            State temporal = new State( new Position(actual.getLuke().getPos_x(), actual.getLuke().getPos_y()), (ArrayList<Position>) actual.getStormtroopers().clone(), (ArrayList<Position>) actual.getWalls().clone(), actual.getMovements(), actual.getSteps());
            temporal.getLuke().setPos_x(temporal.getLuke().getPos_x()+1);
            temporal.setSteps(temporal.getSteps()+1);
            
            if(temporal.getMovements().isEmpty())
                temporal.setMovements(temporal.getMovements()+"S");
            
            else
                temporal.setMovements(temporal.getMovements()+",S");
            
            if(!newStates.add(temporal))
                System.out.println("Elemento no agregado al moverse al Sur");
        }
      
        //Mover Norte
        if( (pos_x-1 >= 0) && (findStorm(actual,pos_x-1,pos_y)) && (findWall(actual,pos_x-1,pos_y)) && (dontBack(actual, 'N')) )
        {
            //Agregar movimiento y actualizar la posicion de Luke
            //Acá hay que tener cuidado con la clonación de objetos, por eso se usó el método clone y se pasaron constructores como parámetros
            State temporal = new State( new Position(actual.getLuke().getPos_x(), actual.getLuke().getPos_y()), (ArrayList<Position>) actual.getStormtroopers().clone(), (ArrayList<Position>) actual.getWalls().clone(), actual.getMovements(), actual.getSteps());
            temporal.getLuke().setPos_x(temporal.getLuke().getPos_x()-1);
            temporal.setSteps(temporal.getSteps()+1);
            
            if(temporal.getMovements().isEmpty())
                temporal.setMovements(temporal.getMovements()+"N");
            
            else
                temporal.setMovements(temporal.getMovements()+",N");
            
            if(!newStates.add(temporal))
                System.out.println("Elemento no agregado al moverse al Norte");
        }
        
        //Mover Este
        
        if( (pos_y+1 < m) && (findStorm(actual,pos_x,pos_y+1)) && (findWall(actual,pos_x,pos_y+1)) && (dontBack(actual, 'E')) )
        {
            //Agregar movimiento y actualizar la posicion de Luke
            //Acá hay que tener cuidado con la clonación de objetos, por eso se usó el método clone y se pasaron constructores como parámetros
            State temporal = new State( new Position(actual.getLuke().getPos_x(), actual.getLuke().getPos_y()), (ArrayList<Position>) actual.getStormtroopers().clone(), (ArrayList<Position>) actual.getWalls().clone(), actual.getMovements(), actual.getSteps());
            temporal.getLuke().setPos_y(temporal.getLuke().getPos_y()+1);
            temporal.setSteps(temporal.getSteps()+1);
            
            if(temporal.getMovements().isEmpty())
                temporal.setMovements(temporal.getMovements()+"E");
            
            else
                temporal.setMovements(temporal.getMovements()+",E");
            
            if(!newStates.add(temporal))
                System.out.println("Elemento no agregado al moverse al Este");;
        }
        
        //Mover Oeste
        if( (pos_y-1 >= 0) && (findStorm(actual,pos_x,pos_y-1)) && (findWall(actual,pos_x,pos_y-1)) && (dontBack(actual, 'O')) )
        {
            //Agregar movimiento y actualizar la posicion de Luke
            //Acá hay que tener cuidado con la clonación de objetos, por eso se usó el método clone y se pasaron constructores como parámetros
            State temporal = new State( new Position(actual.getLuke().getPos_x(), actual.getLuke().getPos_y()), (ArrayList<Position>) actual.getStormtroopers().clone(), (ArrayList<Position>) actual.getWalls().clone(), actual.getMovements(), actual.getSteps());
            temporal.getLuke().setPos_y(temporal.getLuke().getPos_y()-1);
            temporal.setSteps(temporal.getSteps()+1);
            
            if(temporal.getMovements().isEmpty())
                temporal.setMovements(temporal.getMovements()+"O");
            
            else
                temporal.setMovements(temporal.getMovements()+",O");
            
            if(!newStates.add(temporal))
                System.out.println("Elemento no agregado al moverse al Oeste");
        
        
        }
        
        //Verifico si existe exito al usar la fuerza
        forceReturn = force(actual,pos_x,pos_y);
        if(!forceReturn.isEmpty())
        {
            //Agregar movimiento y actualizar la posicion de Luke
            //Acá hay que tener cuidado con la clonación de objetos, por eso se usó el método clone y se pasaron constructores como parámetros
            State temporal = new State( new Position(actual.getLuke().getPos_x(), actual.getLuke().getPos_y()), (ArrayList<Position>) actual.getStormtroopers().clone(), (ArrayList<Position>) actual.getWalls().clone(), actual.getMovements(), actual.getSteps());
            temporal.setSteps(temporal.getSteps()+1);
            
            //Elimino todos los Storm Stroopers en el rango d ela fuerza
            for(int i = 0; i < forceReturn.size();i++)
                temporal.getStormtroopers().remove((int)forceReturn.get(i));
            
            if(temporal.getMovements().isEmpty())
                temporal.setMovements(temporal.getMovements()+"F");
            
            else
                temporal.setMovements(temporal.getMovements()+",F");
            
            if(!newStates.add(temporal))
                System.out.println("Elemento no agregado al usar la fuerza");
        }
        
        //Verifica disparo al norte
        shootValue= shoot(actual,pos_x,pos_y,'N');
        if( (shootValue> -1) )
        {
            //Agregar movimiento y actualizar la posicion de Luke
            //Acá hay que tener cuidado con la clonación de objetos, por eso se usó el método clone y se pasaron constructores como parámetros
            State temporal = new State( new Position(actual.getLuke().getPos_x(), actual.getLuke().getPos_y()), (ArrayList<Position>) actual.getStormtroopers().clone(), (ArrayList<Position>) actual.getWalls().clone(), actual.getMovements(), actual.getSteps());
            temporal.setSteps(temporal.getSteps()+1);
            temporal.getStormtroopers().remove(shootValue); //Se remueve el Storm Strooper dado el indice con shootValue
            
            if(temporal.getMovements().isEmpty())
                temporal.setMovements(temporal.getMovements()+"DN");
            
            else
                temporal.setMovements(temporal.getMovements()+",DN");
           
            if(!newStates.add(temporal))
                System.out.println("Elemento no agregado al disparar al Norte");
        }
        
        //Verifico disparo al sur
        shootValue= shoot(actual,pos_x,pos_y,'S');
        if( (shootValue> -1) )
        {
            //Agregar movimiento y actualizar la posicion de Luke
            //Acá hay que tener cuidado con la clonación de objetos, por eso se usó el método clone y se pasaron constructores como parámetros
            State temporal = new State( new Position(actual.getLuke().getPos_x(), actual.getLuke().getPos_y()), (ArrayList<Position>) actual.getStormtroopers().clone(), (ArrayList<Position>) actual.getWalls().clone(), actual.getMovements(), actual.getSteps());
            temporal.setSteps(temporal.getSteps()+1);
            temporal.getStormtroopers().remove(shootValue); //Se remueve el Storm Strooper dado el indice con shootValue
            
            if(temporal.getMovements().isEmpty())
                temporal.setMovements(temporal.getMovements()+"DS");
            
            else
                temporal.setMovements(temporal.getMovements()+",DS");
           
            if(!newStates.add(temporal))
                System.out.println("Elemento no agregado al disparar al Sur");
        }
        
        //Verifico disparo al Este
        shootValue= shoot(actual,pos_x,pos_y,'E');
        if( (shootValue> -1) )
        {
            //Agregar movimiento y actualizar la posicion de Luke
            //Acá hay que tener cuidado con la clonación de objetos, por eso se usó el método clone y se pasaron constructores como parámetros
            State temporal = new State( new Position(actual.getLuke().getPos_x(), actual.getLuke().getPos_y()), (ArrayList<Position>) actual.getStormtroopers().clone(), (ArrayList<Position>) actual.getWalls().clone(), actual.getMovements(), actual.getSteps());
            temporal.setSteps(temporal.getSteps()+1);
            temporal.getStormtroopers().remove(shootValue); //Se remueve el Storm Strooper dado el indice con shootValue
            
            if(temporal.getMovements().isEmpty())
                temporal.setMovements(temporal.getMovements()+"DE");
            
            else
                temporal.setMovements(temporal.getMovements()+",DE");
           
            if(!newStates.add(temporal))
                System.out.println("Elemento no agregado al disparar al Este");
        }
        
        //Verifico disparo al oeste
        shootValue= shoot(actual,pos_x,pos_y,'O');
        if( (shootValue> -1) )
        {
            //Agregar movimiento y actualizar la posicion de Luke
            //Acá hay que tener cuidado con la clonación de objetos, por eso se usó el método clone y se pasaron constructores como parámetros
            State temporal = new State( new Position(actual.getLuke().getPos_x(), actual.getLuke().getPos_y()), (ArrayList<Position>) actual.getStormtroopers().clone(), (ArrayList<Position>) actual.getWalls().clone(), actual.getMovements(), actual.getSteps());
            temporal.setSteps(temporal.getSteps()+1);
            temporal.getStormtroopers().remove(shootValue); //Se remueve el Storm Strooper dado el indice con shootValue
            
            if(temporal.getMovements().isEmpty())
                temporal.setMovements(temporal.getMovements()+"DO");
            
            else
                temporal.setMovements(temporal.getMovements()+",DO");
           
            if(!newStates.add(temporal))
                System.out.println("Elemento no agregado al disparar al Oeste");
        }
        
        return newStates;
    }
    
    //Función que indica si un Storm Strooper se encuenta en una posicion
    boolean findStorm(State actual,int pos_x, int pos_y)
    {
        for(int i=0; i<actual.getStormtroopers().size();i++)
        {
            if( (pos_x == actual.getStormtroopers().get(i).getPos_x()) && (pos_y == actual.getStormtroopers().get(i).getPos_y()) )                     
                return false;
        }
        
        return true;
    }
     
    //Función que indica si una pared se encuentra en una posición
    boolean findWall(State actual,int pos_x, int pos_y)
    {
        for(int i=0; i<actual.getWalls().size();i++)
        {
            if( (pos_x == actual.getWalls().get(i).getPos_x()) && (pos_y == actual.getWalls().get(i).getPos_y()) )         
                return false;
        }
        
        return true;
    }
    
    
    //Shoot retorna la posición a eliminar en caso de haber disparado a un storm strooper, retorna -1 en caso contrario
    int shoot(State actual,int pos_x,int pos_y, char dir)
    {
        
        //Se debe tomar en consideración que los índices de los stormtroopers no están ordenados, por eso debemos primero recorrer el arreglo compleot y quedarnos con el más cercano
        int umbral, positionarray;
        positionarray=-1;
        
        switch(dir)
        {
            case 'N':
                
                umbral = -1;
                
                //Verifico si existe un Storm Strooper en el norte
                for(int i = 0; i < actual.getStormtroopers().size();i++)
                {
                    if( (actual.getStormtroopers().get(i).getPos_y() == pos_y) && (actual.getStormtroopers().get(i).getPos_x() < pos_x) && (actual.getStormtroopers().get(i).getPos_x() > umbral) )
                    {
                        umbral = actual.getStormtroopers().get(i).getPos_x();
                        positionarray=i;
                    }
                }
                
                //Reviso si efectivamente existe un Storm Strooper al norte
                if(positionarray > -1)
                {
                    //Ahora reviso si existe una pared que impida dispararle
                    
                    for(int i = 0; i < actual.getWalls().size();i++)
                    {
                        if( (actual.getWalls().get(i).getPos_y() == actual.getLuke().getPos_y()) && (actual.getWalls().get(i).getPos_x() < pos_x ) && (actual.getWalls().get(i).getPos_x() > umbral) )
                            return -1;
                    }
                    
                    //Si no existe Pared retorno la posición en la que está el stormstrooper
                    
                    return positionarray;
                }
         
                break;
                
            case 'S':
                
                umbral = n; //El umbral es un número mayor a las dimensiones del tablero verticalmente
                
                //Verifico si existe un Storm Strooper en el sur
                for(int i = 0; i < actual.getStormtroopers().size();i++)
                {
                    if( (actual.getStormtroopers().get(i).getPos_y() == pos_y) && (actual.getStormtroopers().get(i).getPos_x() > pos_x) && (actual.getStormtroopers().get(i).getPos_x() < umbral) )
                    {
                        umbral = actual.getStormtroopers().get(i).getPos_x();
                        positionarray=i;
                    }
                }
                
                //Reviso si efectivamente existe un Storm Strooper al sur
                if(positionarray > -1)
                {
                    //Ahora reviso si existe una pared que impida dispararle
                    
                    for(int i = 0; i < actual.getWalls().size();i++)
                    {
                        if( (actual.getWalls().get(i).getPos_y() == actual.getLuke().getPos_y()) && (actual.getWalls().get(i).getPos_x() > pos_x ) && (actual.getWalls().get(i).getPos_x() < umbral) )
                            return -1;
                    }
                    
                    //Si no existe Pared retorno la posición en la que está el stormstrooper
                    return positionarray;
                }
         
                break;
                
            case 'E':
                
                umbral = m; //El umbral es un número mayor a las dimensiones del tablero verticalmente
                
                //Verifico si existe un Storm Strooper en el este
                for(int i = 0; i < actual.getStormtroopers().size();i++)
                {
                    if( (actual.getStormtroopers().get(i).getPos_x() == pos_x) && (actual.getStormtroopers().get(i).getPos_y() > pos_y) && (actual.getStormtroopers().get(i).getPos_y() < umbral) )
                    {
                        umbral = actual.getStormtroopers().get(i).getPos_y();
                        positionarray=i;
                    }
                }
                
                //Reviso si efectivamente existe un Storm Strooper al este
                if(positionarray > -1)
                {
                    //Ahora reviso si existe una pared que impida dispararle
                    
                    for(int i = 0; i < actual.getWalls().size();i++)
                    {
                        if( (actual.getWalls().get(i).getPos_x() == actual.getLuke().getPos_x()) && (actual.getWalls().get(i).getPos_y() > pos_y ) && (actual.getWalls().get(i).getPos_y() < umbral) )
                            return -1;
                    }
                    
                    //Si no existe Pared retorno la posición en la que está el stormstrooper
                    return positionarray;
                }
         
                break;
                
            case 'O':
                
                umbral = -1;
                
                //Verifico si existe un Storm Strooper en el oeste
                for(int i = 0; i < actual.getStormtroopers().size();i++)
                {
                    if( (actual.getStormtroopers().get(i).getPos_x() == pos_x) && (actual.getStormtroopers().get(i).getPos_y() < pos_y) && (actual.getStormtroopers().get(i).getPos_y() > umbral) )
                    {
                        umbral = actual.getStormtroopers().get(i).getPos_y();
                        positionarray=i;
                    }
                }
                
                //Reviso si efectivamente existe un Storm Strooper al oeste
                if(positionarray > -1)
                {
                    //Ahora reviso si existe una pared que impida dispararle
                    
                    for(int i = 0; i < actual.getWalls().size();i++)
                    {
                        if( (actual.getWalls().get(i).getPos_x() == actual.getLuke().getPos_x()) && (actual.getWalls().get(i).getPos_y() < pos_y ) && (actual.getWalls().get(i).getPos_y() > umbral) )
                            return -1;
                    }
                    
                    //Si no existe Pared retorno la posición en la que está el stormstrooper
                    
                    return positionarray;
                }
         
                break;
                    
            default:
                
                System.out.println("Movimiento no válido");
        }
        
        return -1;
    }
    
    //Función que retonar un arreglo de enteros con las posiciones de los Storm Strooper a eliminar
    ArrayList<Integer> force(State actual,int pos_x, int pos_y)
    {
        ArrayList<Integer> returnValue = new ArrayList();
        Position pos_N, pos_S, pos_E, pos_O, pos_NE, pos_NO, pos_SE, pos_SO;
        
        //Se buscan las posiciones de todos los posibles movimientos
        
        pos_N = new Position(pos_x+1,pos_y);
        pos_S = new Position(pos_x-1,pos_y);
        pos_E = new Position(pos_x,pos_y+1);
        pos_O = new Position(pos_x,pos_y-1);
        pos_NE = new Position(pos_x-1,pos_y+1);
        pos_NO = new Position(pos_x-1, pos_y-1);
        pos_SE = new Position(pos_x+1,pos_y+1);
        pos_SO = new Position(pos_x+1,pos_y-1);
        
        for(int i = 0; i < actual.getStormtroopers().size();i++)
        {
            if( (actual.getStormtroopers().get(i).getPos_x() == pos_N.getPos_x()) && (actual.getStormtroopers().get(i).getPos_y() == pos_N.getPos_y()) ) //Norte
            {
                    //Como se retorna un arreglo con las posiciones a eliminar y al eliminar un objeto de una lista los indices cambia,
                    //Las posiciones se añaden anticipando el estado luego de eliminar un objeto
                    //Por eso se añade el indice - el tamaño del arreglo de retorno
                    returnValue.add(i - returnValue.size());
            
            }else if( (actual.getStormtroopers().get(i).getPos_x() == pos_S.getPos_x()) && (actual.getStormtroopers().get(i).getPos_y() == pos_S.getPos_y()) ) //Sur
            {
                //Como se retorna un arreglo con las posiciones a eliminar y al eliminar un objeto de una lista los indices cambia,
                    //Las posiciones se añaden anticipando el estado luego de eliminar un objeto
                    //Por eso se añade el indice - el tamaño del arreglo de retorno
                returnValue.add(i - returnValue.size());
            
            }else if( (actual.getStormtroopers().get(i).getPos_x() == pos_E.getPos_x()) && (actual.getStormtroopers().get(i).getPos_y() == pos_E.getPos_y()) ) //Este
            {
                //Como se retorna un arreglo con las posiciones a eliminar y al eliminar un objeto de una lista los indices cambia,
                    //Las posiciones se añaden anticipando el estado luego de eliminar un objeto
                    //Por eso se añade el indice - el tamaño del arreglo de retorno
                returnValue.add(i - returnValue.size());
            
            }else if( (actual.getStormtroopers().get(i).getPos_x() == pos_O.getPos_x()) && (actual.getStormtroopers().get(i).getPos_y() == pos_O.getPos_y()) ) //Oeste
            {
                //Como se retorna un arreglo con las posiciones a eliminar y al eliminar un objeto de una lista los indices cambia,
                    //Las posiciones se añaden anticipando el estado luego de eliminar un objeto
                    //Por eso se añade el indice - el tamaño del arreglo de retorno
                returnValue.add(i - returnValue.size());
 
            }else if( (actual.getStormtroopers().get(i).getPos_x() == pos_NE.getPos_x()) && (actual.getStormtroopers().get(i).getPos_y() == pos_NE.getPos_y()) ) //NorEste
            {
                //Como se retorna un arreglo con las posiciones a eliminar y al eliminar un objeto de una lista los indices cambia,
                    //Las posiciones se añaden anticipando el estado luego de eliminar un objeto
                    //Por eso se añade el indice - el tamaño del arreglo de retorno
                returnValue.add(i - returnValue.size());
            
            }else if( (actual.getStormtroopers().get(i).getPos_x() == pos_NO.getPos_x()) && (actual.getStormtroopers().get(i).getPos_y() == pos_NO.getPos_y()) ) //NorOeste
            {
                //Como se retorna un arreglo con las posiciones a eliminar y al eliminar un objeto de una lista los indices cambia,
                    //Las posiciones se añaden anticipando el estado luego de eliminar un objeto
                    //Por eso se añade el indice - el tamaño del arreglo de retorno
                returnValue.add(i - returnValue.size());
            
            }else if( (actual.getStormtroopers().get(i).getPos_x() == pos_SE.getPos_x()) && (actual.getStormtroopers().get(i).getPos_y() == pos_SE.getPos_y()) ) //SurEste
            {
                //Como se retorna un arreglo con las posiciones a eliminar y al eliminar un objeto de una lista los indices cambia,
                    //Las posiciones se añaden anticipando el estado luego de eliminar un objeto
                    //Por eso se añade el indice - el tamaño del arreglo de retorno
                returnValue.add(i - returnValue.size());
            
            }else if( (actual.getStormtroopers().get(i).getPos_x() == pos_SO.getPos_x()) && (actual.getStormtroopers().get(i).getPos_y() == pos_SO.getPos_y()) ) //SurOeste
            {
                //Como se retorna un arreglo con las posiciones a eliminar y al eliminar un objeto de una lista los indices cambia,
                    //Las posiciones se añaden anticipando el estado luego de eliminar un objeto
                    //Por eso se añade el indice - el tamaño del arreglo de retorno
                returnValue.add(i - returnValue.size());
            
            }
        }
        
        return returnValue;
    }
    
    boolean dontBack(State actual, char movement)
    {
        if(actual.getMovements().length() > 0)
        {
            switch(movement)
            {
                case 'N':
                    
                    if(actual.getMovements().charAt(actual.getMovements().length()-1) == 'S')
                        return false;
                    
                    break;
                    
                case 'S':
                    
                    if(actual.getMovements().charAt(actual.getMovements().length()-1) == 'N')
                        return false;
                    
                    break;
                    
                case 'E':
                    
                    if(actual.getMovements().charAt(actual.getMovements().length()-1) == 'O')
                        return false;
                    
                    break;
                    
                case 'O':
                    
                    if(actual.getMovements().charAt(actual.getMovements().length()-1) == 'E')
                        return false;
                    
                    break;
            }
        }
    
        return true;
    }
    
    public State init()
    {
        State returnvalue = null;
        Position luke = null;
        ArrayList<Position> stormtroopers = new ArrayList<Position>();
        ArrayList<Position> walls = new ArrayList<Position>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String linea;

        try 
        {
           archivo = new File ("file.in");
           fr = new FileReader (archivo);
           br = new BufferedReader(fr);

           // Lectura del fichero, toma los parametros n.m y limit
           linea=br.readLine();
           String[] data = linea.split(" ");
           this.n = Integer.parseInt(data[0]);
           this.m = Integer.parseInt(data[1]);
           this.limit = Integer.parseInt(data[2]);
           
           for(int i=0; i<n; i++)
           {
               linea=br.readLine();
             
               for(int j=0; j<m; j++)
               {
                   if(linea.charAt(j)=='L')
                       luke = new Position(i, j);
                   
                   if(linea.charAt(j)=='S')
                   {
                       Position stormtrooper = new Position(i, j);
                       stormtroopers.add(stormtrooper);
                   }
                   
                   if(linea.charAt(j)=='*')
                   {
                       Position wall = new Position(i, j);
                       walls.add(wall);
                   }
               }
           }
           returnvalue = new State(luke, stormtroopers, walls, new String(), 0);
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
        return returnvalue;
   }   
}
