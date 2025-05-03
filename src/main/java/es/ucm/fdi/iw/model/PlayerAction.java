package es.ucm.fdi.iw.model;

public class PlayerAction {
    private ActionType actionType;  // Tipo de acción (BUY_UNIT, SELL_UNIT, etc.)
    private String playerName;      // Nombre del jugador que realiza la acción
    private Object actionDetails;   // Detalles de la acción (puede ser un objeto, unidad, mensaje, etc.)

    // Constructor para inicializar los valores
    public PlayerAction(ActionType actionType, String playerName, Object actionDetails) {
        this.actionType = actionType;
        this.playerName = playerName;
        this.actionDetails = actionDetails;
    }

    // Getters y setters
    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Object getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(Object actionDetails) {
        this.actionDetails = actionDetails;
    }


    // Enum dentro de la clase PlayerAction
    public enum ActionType {
        BUY_UNIT,
        SELL_UNIT,
        BUY_ITEM,
        SELL_ITEM,
        ASSIGN_ITEM_TO_UNIT,
        REFRESH_SHOP,
        SEND_MESSAGE, GENERAL
    }
}



