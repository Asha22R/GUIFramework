package com.comcast.crm.organizationtest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.comcast.crm.generic.basetest.BaseClass;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.objectrepositoryutility.ContactInformationPage;
import com.comcast.crm.objectrepositoryutility.CreateNewProductPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.OrganizationInformationPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;
import com.comcast.crm.objectrepositoryutility.ProductsPage;
import com.comcast.crm.objectrepositoryutility.moreInformationPage;

public class createAQuoteWithContactName extends BaseClass {
	
	@Test
	public void  user_is_able_to_add_a_note() throws Throwable
	{

		HomePage hp=new HomePage(driver);
		
		/* Create New Product*/
		
		hp.getProductsLink().click();
		ProductsPage pp=new ProductsPage(driver);
		pp.getCreateProductImg().click();

		CreateNewProductPage cnp=new CreateNewProductPage(driver);
		String productName= eLib.getDataFromExcel("Organizations",13,2);
		cnp.getProductnameEdt().sendKeys(productName);
		cnp.getSaveBtn().click();
		
		/**
		 * Navigating organization page
		 */
		hp.getOrgLink().click();
		String createOrgText = eLib.getDataFromExcel("Organizations", 13, 3);
		
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		
		CreatingNewOrganizationPage newOrgPage = new CreatingNewOrganizationPage(driver);
		String createNewOrgPage = newOrgPage.getCreatingNewOrganizationText().getText();
		Assert.assertEquals(createOrgText, createNewOrgPage);
		
		/**
		 * generating random number
		 */
		
		JavaUtility ju = new JavaUtility();
		int num = ju.getRandomNumber();
		
		String orgName = eLib.getDataFromExcel("Organizations", 13, 4) + num;
		
		/** creating organization and validation
		 * 
		 */
		
		newOrgPage.createOrg(orgName);
		Thread.sleep(3000);
		String OrganizationInformationText = eLib.getDataFromExcel("Organizations", 13, 5);
		System.out.println(OrganizationInformationText);
		
		OrganizationInformationPage oip = new OrganizationInformationPage(driver);
		String TosterMessage = oip.getHeaderMsg().getText();
		System.out.println(TosterMessage);
		
		boolean getBoolean = TosterMessage.contains(OrganizationInformationText);
		Assert.assertTrue(getBoolean);
		
		oip.getmoreInformationLink().click();

		String moreInfoText = eLib.getDataFromExcel("Organizations", 13, 6);

		

		moreInformationPage mip = new moreInformationPage(driver);
		mip.getcontactsTab().click();
		
		String moreInformationPageText = mip.getHeaderMsg().getText();

		boolean getBooleanValue = moreInformationPageText.contains(moreInfoText);
		Assert.assertTrue(getBooleanValue);

		

		/**
		 * creating contact from more info page
		 */
		
		mip.getcontactsTab().isDisplayed();
		String contactText = eLib.getDataFromExcel("Organizations", 13, 7);
		mip.getcontactsTab().click();
		mip.getaddcontactButton().click();

		ContactInformationPage cip = new ContactInformationPage(driver);
		String creatingNewContactText = cip.getHeaderTxt().getText();
		Assert.assertEquals(creatingNewContactText, contactText);

		String contactName = eLib.getDataFromExcel("Organizations", 13, 8) + num;
		cip.createContact(contactName);
		driver.findElement(By.xpath("//tr[@bgcolor=\"white\"]//td//a[text()='" + contactName + "']")).isDisplayed();
		
		

	}
}
