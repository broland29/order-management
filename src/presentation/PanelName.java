package presentation;

/**Enumeration holding names of all panels
 * <p>Makes referring to panels by their name less error-prone</p>
 * <p>Concrete usage: switching Card Layout, memorizing last panel</p>
 */
public enum PanelName {
    WELCOME_PANEL,

    CLIENT_OPTION_PANEL,
    ORDER_OPTION_PANEL,
    PRODUCT_OPTION_PANEL,

    CLIENT_VIEW_PANEL,
    ORDER_VIEW_PANEL,
    PRODUCT_VIEW_PANEL,

    CLIENT_DELETE_PANEL,
    ORDER_DELETE_PANEL,
    PRODUCT_DELETE_PANEL,

    CLIENT_ADD_PANEL,
    ORDER_ADD_PANEL,
    PRODUCT_ADD_PANEL,

    CLIENT_EDIT_PANEL,
    ORDER_EDIT_PANEL,
    PRODUCT_EDIT_PANEL
}
