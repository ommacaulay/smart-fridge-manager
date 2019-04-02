package com.macaulay.olajide.smartfridgemanager.service;

/**
 * Interface for the Smart Fridge Manager
 *
 */
public interface SmartFridgeManagerService {

    /**
     * Event Handlers - These are methods invoked by the SmartFridge hardware to send notification of items that have
     * been added and/or removed from the fridge. Every time an item is removed by the fridge user, it will emit a
     * handleItemRemoved() event to this class, every time a new item is added or a previously removed item is re-inserted,
     * the fridge will emit a handleItemAdded() event with its updated fillFactor.
     */

    /**
     * This method is called every time an item is removed from the fridge
     *
     * @param itemUUID
     */
    void handleItemRemoved( String itemUUID );

    /**
     * This method is called every time an item is stored in the fridge
     *
     * @param itemType
     * @param itemUUID
     * @param name
     * @param fillFactor
     */
    void handleItemAdded( long itemType, String itemUUID, String name, Double fillFactor );

    /**
     * These are the query methods for the fridge to be able to display alerts and create shopping
     * lists for the fridge user.
     */

    /**
     * Returns a list of items based on their fill factor. This method is used by the
     * fridge to display items that are running low and need to be replenished.
     *
     * i.e.
     *      getItems( 0.5 ) - will return any items that are 50% or less full, including
     *                        items that are depleted.
     *
     * @return an array of arrays containing [itemType]
     */
    Object[] getItems( Double fillFactor );

    /**
     * Returns the fill factor for a given item type to be displayed to the owner.
     *
     * @param itemType
     *
     * @return a double representing the fill factor for the type
     */
    Double getFillFactor( long itemType );

    /**
     * Stop tracking a given type. This method is used by the fridge to signal that its
     * owner will no longer stock this type and thus should not be returned from #getItems()
     *
     * @param itemType
     */
    void forgetType(long itemType );

}
