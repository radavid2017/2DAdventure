package features;

import game.GamePanel;
import object.SuperObject;
import tile.Tile;
import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {
    public double worldX;
    public double worldY;
    public double screenX;
    public double screenY;
    public static GamePanel gPanel;

    public Camera(int worldX, int worldY, double screenX, double screenY, GamePanel gPanel) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.screenX = screenX;
        this.screenY = screenY;
        Camera.gPanel = gPanel;
    }

    public Camera(double worldX, double worldY, double screenX, double screenY, GamePanel gPanel) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.screenX = screenX;
        this.screenY = screenY;
        Camera.gPanel = gPanel;
    }

    public void manageTiles(Graphics2D g2D, TileManager tiles, int idTileMap) {
        if (worldX + gPanel.tileSize > gPanel.player.worldX - gPanel.player.screenX &&
                worldX - gPanel.tileSize < gPanel.player.worldX + gPanel.player.screenX &&
                worldY + gPanel.tileSize > gPanel.player.worldY - gPanel.player.screenY &&
                worldY - gPanel.tileSize < gPanel.player.worldY + gPanel.player.screenY) {

            boolean gasit = false;
            for (Tile tile : tiles.generalTiles) {
                if (tile.idTile == idTileMap) {
                    g2D.drawImage(tile.image, (int) screenX, (int) screenY, null);
                    gasit = true;
                    break;
                }
            }
            if (!gasit)
                System.out.println("TILE NEGASIT");
//            g2D.drawImage(tiles.generalTiles.get(tileNum).image, (int) screenX, (int) screenY, null);
        }
    }

    public void manageObjects(Graphics2D g2D, BufferedImage image) {
        if (worldX + gPanel.tileSize > gPanel.player.worldX - gPanel.player.screenX &&
                worldX - gPanel.tileSize < gPanel.player.worldX + gPanel.player.screenX &&
                worldY + gPanel.tileSize > gPanel.player.worldY - gPanel.player.screenY &&
                worldY - gPanel.tileSize < gPanel.player.worldY + gPanel.player.screenY) {

            g2D.drawImage(image, (int) screenX, (int) screenY, null);
        }
    }

    public static void zoomInOut(int i) {
//        System.out.println("default zoom: " + gPanel.defaultZoom);

        boolean canScale = true;
//        System.out.println("player inainte de zoom: " + gPanel.player.worldX + " " + gPanel.player.worldY);

        int oldWorldWidth = gPanel.tileSize * gPanel.maxWorldCol; // 2400
        if (gPanel.tileSize * gPanel.maxWorldCol > gPanel.limitZoomOut && gPanel.tileSize * gPanel.maxWorldCol < gPanel.limitZoomIn) gPanel.tileSize += i; // actualizare marime texturi
        else if (gPanel.tileSize * gPanel.maxWorldCol == gPanel.limitZoomOut) {
            gPanel.tileSize++;
            canScale = false;
        }
        else if (gPanel.tileSize * gPanel.maxWorldCol == gPanel.limitZoomIn) {
            gPanel.tileSize--;
            canScale = false;
        }
        else {
            System.out.println("zoomInOut a intrat pe return!");return;
        }
        int newWorldWidth = gPanel.tileSize * gPanel.maxWorldCol; // 2350
        gPanel.player.speed = (double) newWorldWidth / 600;
        double mul = (double) newWorldWidth / oldWorldWidth; // 0.9791(6)

        double newPlayerWorldX = gPanel.player.worldX * mul;
        double newPlayerWorldY = gPanel.player.worldY * mul;

        // actualizare marime zoom pentru jucator
        gPanel.player.worldX = newPlayerWorldX;
        gPanel.player.worldY = newPlayerWorldY;

        // actualizare marime zoom pentru aria de coliziune a jucatorului
        int nr = gPanel.tileSize*gPanel.maxWorldCol;
        if (canScale && nr > gPanel.limitZoomOut+150 && nr < gPanel.limitZoomIn-100) {
            gPanel.player.solidArea.width += i * mul;
            gPanel.player.solidArea.height += i * mul;
        }
//        gPanel.player.solidArea.x += mul;
//        gPanel.player.solidArea.y += mul;

//        System.out.println("player: " + gPanel.player.worldX + " " + gPanel.player.worldY);
//        System.out.println("solidArea: " + gPanel.player.solidArea.x + " " + gPanel.player.solidArea.y);

        // actualizare marime zoom pentru obiecte
        for (SuperObject object : gPanel.objects) {
            double newObjWorldX = object.worldX * mul;
            double newObjWorldY = object.worldY * mul;
            object.worldX = newObjWorldX;
            object.worldY = newObjWorldY;
        }
    }
}