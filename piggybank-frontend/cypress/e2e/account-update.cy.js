describe('Account Update Through Settings', () => {
    it('updates account information', () => {
        cy.visit('http://localhost:3000/login');
        cy.contains('Melvin').click();
        cy.contains('Instellingen').click();
        cy.wait(500);
        cy.get('input[name="accountName"]').clear().type('Rekening van Sil');
        cy.get('button[type="submit"]').click();
        cy.contains('👍 Het is gelukt om je account bij te werken.').should('be.visible');

        cy.visit('http://localhost:3000');
        cy.contains('Rekening van Sil').should('be.visible');
    });
});
