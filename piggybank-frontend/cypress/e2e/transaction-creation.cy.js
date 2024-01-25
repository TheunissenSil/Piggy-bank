describe('Account Update Through Settings', () => {
    it('updates account information', () => {
        cy.visit('http://localhost:3000/login');
        cy.contains('Melvin').click();
        cy.contains('Overboeken').click();
        cy.wait(500);
        cy.get('select[name="account"]').find('option').first().then((element) => {
            cy.get('select[name="account"]').select(element.val());
        });
        cy.get('select[name="toaccount"]').select('Cem Fuijk');
        cy.get('select[name="currency"]').select('€');
        cy.get('input[name="amount"]').clear().type('100');
        cy.get('textarea[name="description"]').clear().type('Test');
        cy.get('button[type="submit"]').click();

        cy.contains('👍 Het is gelukt om € 100 over te maken!').should('be.visible');

        cy.visit('http://localhost:3000/transactions');
        cy.contains('Test').should('be.visible');
        cy.contains('€ -100').should('be.visible');
    });
});
