/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Cell;

import Model.Direction;
import Model.GameElement.GameElement;
import Model.GameElement.GameElementDeathEvent;
import Model.GameElement.Ghost;
import Model.GameElement.MovingElement;
import Model.GameElement.Pacman;
import Model.GameElement.StaticElement;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Regi
 */
public abstract class TraversableCell extends Cell {

    private ArrayList<GameElement> elements = new ArrayList();

    public TraversableCell(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public abstract Direction[] getPossibleDirections();

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.translate(positionX * CELL_SIZE, positionY * CELL_SIZE);
        drawCell(g);
        for (GameElement element : elements) {
            element.draw(g);
        }
        g.translate(-positionX * CELL_SIZE, -positionY * CELL_SIZE);
    }
    
    protected void drawCell(Graphics g){
        g.drawRect(0, 0, CELL_SIZE, CELL_SIZE);
    }

    public void addMover(MovingElement mover) {
        elements.add(mover);

        for (int i = 0; i < elements.size(); i++) {
            
            GameElementDeathEvent e = elements.get(i).moverEnteredCell(mover);

            if (e != null) {
                if(elements.indexOf(e) <= i){
                    i--;
                }
                removeElement(e.GetElement());
                if (e.GetElement() == mover) {
                    break;
                }
            }
        }
    }

    public void removeElement(GameElement element) {
        elements.remove(element);
    }

    public void addStatic(StaticElement staticElement) {
        elements.add(staticElement);
    }

    public boolean isPossibleDirection(Direction direction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
